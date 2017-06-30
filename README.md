[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b520d4ca49c84e14bfde23e46cb1b213)](https://www.codacy.com/app/nicolasmanic/Facegram?utm_source=github.com&utm_medium=referral&utm_content=nicolasmanic/Facegram&utm_campaign=badger)
[![Build Status](https://travis-ci.org/nicolasmanic/Facegram.svg?branch=master)](https://travis-ci.org/nicolasmanic/Facegram)
[![codecov](https://codecov.io/gh/nicolasmanic/Facegram/branch/master/graph/badge.svg)](https://codecov.io/gh/nicolasmanic/Facegram)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b520d4ca49c84e14bfde23e46cb1b213)](https://www.codacy.com/app/nicolasmanic/Facegram?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nicolasmanic/Facegram&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nicolasmanic/Facegram/blob/master/LICENCE)
# Facegram

**A location based social network**

This is a PoC application, which demonstrates how Spring Boot, Spring Cloud (and the Netflix stack) can be used in a 
Microservice Architecture.

## Technology Stack

### Service Discovery (Eureka)

In a microservice architecture services typically need to call each other. When we consider the dynamic nature of a
typical microservice-based application we can easily identify a number of potential issues. Most importantly how dynamically 
assigned endpoints are being used and how load balancing works.

To solve this problem I use Netflix Eureka as a client-side discovery pattern. With spring boot and `spring-cloud-starter-eureka-server`
the required setup for a Eureka server is limited, with `@EnableEurekaServer` and few configuration lines the registry
server is up and running. Client configuration is also trivial, only `@EnableEurekaClient` annotation and the application 
name is required.

```yaml
spring:
  application:
    name: story-service
```

Each client is responsible for registering at Eureka server on startup. When a service wants to communicate with another service
it can refer to it by using the spring.application.name, Eureka is responsible for providing the correct endpoint, load balancing and
health checking for each service.

Eureka endpoint: [localhost:8761](http://localhost:8761)

### API Gateway (Zuul)

In a microservice architecture, consumers need a unified interfaces of the system. The scattered microservices of our
system should not be visible to the outside world.

In order to address this issue Netflix developed and open-sourced Zuul proxy server. This is the entry point into the
system, which allows consumers (mobile, browser etc.) to consume services from multiple hosts without having to manage
CORS and authentication for each service.

In this project Zuul is used to server (minimal) static content, route requests to appropriate services and log request/responses
using filters. In order to enable Zuul the `@EnableZuulProxy` annotation is needed. The routing is being done by the following 
sample configuration:

``` yaml
zuul:
  routes:
    story-service:
      path: /story-service/**
      serviceId: story-service
      strip-prefix: false
```

The above configuration means that every request starting with /story-service will be forward to the service with
serviceId: story-service. Zuul leverage Eureka & Ribbon/Hystrix for locating the services and load balancing the requests.

Zuul endpoint: [localhost:8765](http://localhost:8765)

### Monitoring (Hystrix Dashboard)

In an infrastructure with dozen of microservices is crucial to be able to monitor the state of each service. It is only
logical to use Hystrix Dashboard for this project as Hystrix is used as a Circuit Breaker in all the services.

With Hystrix Dashboard we can have a quick overview of all services. For enabling Hystrix Dashboard the following
annotations are needed:

- `@EnableHystrixDashboard` 
- `@EnableTurbine` 
- `@EnableEurekaClient` 

You also need to add the following configuration for setting up turbine.

``` yaml
turbine:
#use instanceUrlSuffix in order to specify the contextPath of each microservice
  instanceUrlSuffix:
    STORY-SERVICE: /story-service/hystrix.stream
  aggregator:
    clusterConfig: STORY-SERVICE
  appConfig: STORY-SERVICE
  combineHostPort: true
```

In order to access the console go to: [localhost:8179/hystrix](localhost:8179/hystrix) and add the turbine URL `turbine.stream?cluster=STORY-SERVICE``
(for story service).

Turbine is not build to aggregate multiple services into the same cluster. To do this you need to add in each of the clients
the dependency of spring-boot-starter-amqp and created a rabbitMQ broker. Turbine is aggregating all messages via amqp
so you can be able to see all Hystrix commands aggregated on hystrix dashboard server

### Security (OAuth2)

In this project OAuth2 framework is used for authorization. The Auth server is responsible for granting OAuth2 tokens that
can be used to access certain resources. It is also responsible for securing service-to-service communication.

In this project we use 2 grant types:
- Password
- Client Credentials

The `password` grand type is used for the communication between consumers and the API, while `client credentials` is used for 
service-to-service communication.

In order to secure the endpoints that need to be accessed only by other services we use 2 scope types:
- ui 
- server 

When server scope is used means that this endpoint can only be called by another service and the functionality is enforced by
the use of `@PreAuthorize("#oauth2.hasScope('server')")`. 
Another way to secure sensitive endpoints is by enforcing "user roles" to them, this can be done by using `@PreAuthorize("hasRole('ROLE_ADMIN')")`.
Endpoints annotated as per the above example can only be accessed by clients with this type of role. In this Project there are 2 roles:
- User (default)
- Admin 

### Circuit Breaker (Hystrix)

Fast failing and fault tolerance are two important aspects of a microservices system. For this reason we use Hystrix a
Circuit Breaker pattern implementation from Netfix. 

Hystrix helps to avoid cascading an issue over the network, this is especially important when there are
multiples services and a small (recoverable) exception may cause multiple failures and magnify the initial problem.

Another functionality of Hystrix is the ability to provide fallbacks. In case of a failure the system may self-recover by
falling back to a default behavior.

To enable Hystrix you need `@EnableHystrix` annotation and `@HystrixCommand(fallbackMethod = "fallbackMethod"` for each 
method you want to wrap with Hystrix, here you have and a fallback method called `fallbackMethod` which provided a default
behavior. In this project many fallback methods retrieve (in a naively way) previous results from cache.


### Client Side Load Balancing (Ribbon)

To avoid using an extra load balancer (as well as an extra hop) Spring Cloud uses natively Ribbon. Eureka provided a dynamic list
of all available services (and service instances) and Ribbon is responsible for balancing between each instance.

### Http Client (Feign)

Feign is a declarative HTTP client developed by Netflix. It uses Ribbon and Hystrix for load balancing and circuit breaking.
All is needed is an annotated interface and the actual implementation will be provisioned at runtime. 

To set up Feing you need the `@EnableFeignClients` annotation and an interface like the following

``` java
@FeignClient(value = "story-service", fallbackFactory = HystrixClientFallbackFactory.class)
@RequestMapping("/story-service")
public interface StoryClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hotStories/user/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStoriesOfUser(@PathVariable("username") String username);
}
```

The value `@FeingClient` specifies the client serviceId (auto-discovered from Eureka).

## Topology
![topology](https://user-images.githubusercontent.com/4174162/27611807-534182c6-5b9c-11e7-986e-0bd60b6b56b4.png)

## API

#### Authorization Server

Responsible for authorization and authentication of consumers & services.

| Method | Path	| URL Parameters | Data Parameters  | Description | User authenticated |
|:------:|:----:|:--------------:|:----------------:|------------------------|:------------------:|
|POST	| /uaa/oauth/token	| `grand_type`&`username`&`password` | 	|   Login and retrieve bearer token   |  NO|  	
|GET	| /uaa/users/current	| 	| 	|   Get user principal   | YES|

#### User Service

Responsible for managing the user lifecycle and social actions (follow, unfollow, subscribe etc.). Also aggregates and retrieve stories
based on user & location.

| Method | Path	| URL Parameters | Data Parameters (Json)  | Description | User authenticated |
|:------:|:----:|:--------------:|:----------------:|------------------------|:------------------:|
|POST	| /user-service/administrative/createUser |  | `UserRequest` | User creation at `User-Service` & `Auth-Server`  |  NO|
|POST	| /user-service/administrative/updateUser |  | `UserRequest` | Updates User details  |  NO|
|GET	| /user-service/administrative/retrieveUser/{*username*} | username |  | Retrieves User based on give username  |  YES|
|DELETE	| /user-service/administrative/deleteUser/{*username*} | username |  | Delete User (This action is available only for users with Admin Role)  |  YES|
|POST	| /user-service/administrative/addFollowing/{*username*} | username |  | Follow user  |  YES|
|DELETE	| /user-service/administrative/removeFollowing/{*username*} | username |  | Unfollow user  |  YES|
|GET	| /user-service/administrative/retrieveFollowings/{*username*} | username |  | Retrieves following users  |  YES|





#### Story Service

Stores information and manages the lifecycle of the story. It is also responsible for sorting and retrieving stories based on
a number of criteria (Location, User, Group etc.).

| Method | Path	| URL Parameters | Data Parameters (Json)  | Description | User authenticated |
|:------:|:----:|:--------------:|:----------------:|------------------------|:------------------:|
|POST	| /story-service/story/create	|  | `StoryRequest` |  Persist Story to DB   |  YES|  
|GET	| /story-service/story/fetch/{*StoryId*}	| StoryId | |  Retrieve Story   |  YES| 
|DELETE	| /story-service/story/delete/{*StoryId*}	| StoryId | |  Delete Story   |  YES| 
|POST	| /story-service/story/like/{*StoryId*}	| StoryId | |  Like Story   |  YES| 
|POST	| /story-service/story/unlike/{*StoryId*}	| StoryId | |  Unlike Story   |  YES| 
|POST	| /story-service/story/comment/{*StoryId*} | StoryId | `CommentRequest` |  Comment Story, comments are stored in Story collection |  YES|
|DELETE	| /story-service/story/uncomment/{*CommentId*} | CommentId | | Remove comment from Story |  YES|
|GET	| /story-service/story/fetchComment/{*CommentId*}	| CommentId | |  Retrieve Comment   |  YES| 



Note: 
- *Top stories*: Stories sorted by likes.
- *Hot stories*: Stories sorted by comments.
- *New stories*: Stories sorted by creation date.

#### Group Service

Responsible for managing the group lifecycle. Also aggregates group stories based on type (new/top/hot).

| Method | Path	| URL Parameters | Data Parameters  | Description | User authenticated |
|:------:|:----:|:--------------:|:----------------:|:-----------:|:------------------:|
|GET	| /test	|  |  |   TEST   |  NO|  	

## How to run

After successful building the project with maven you first need run each microservice in the following order:

1. discovery-service
2. auth-server
3. edge-server
4. monitoring-server
5. group-service
6. story-service
7. user-service

You will also need 4 Mongo DB instances (Auth-server, Group-service, Story-service, User-service)

**Todo**:
- [ ] Add automation script
- [ ] Build docker images

## Feedback

For any feedback please contact me at [linkedin](https://www.linkedin.com/in/nick-kanakis-24b34677)




