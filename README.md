[![Build Status](https://travis-ci.org/nicolasmanic/Facegram.svg?branch=master)](https://travis-ci.org/nicolasmanic/Facegram)
[![codecov](https://codecov.io/gh/nicolasmanic/Facegram/branch/master/graph/badge.svg)](https://codecov.io/gh/nicolasmanic/Facegram)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b520d4ca49c84e14bfde23e46cb1b213)](https://www.codacy.com/app/nicolasmanic/Facegram?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nicolasmanic/Facegram&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nicolasmanic/Facegram/blob/master/LICENCE)
# Facegram

**A location based social network**

This is a PoC application, which demonstrates how Spring Boot, Spring Cloud (and the Netflix stack) can be used in a 
Microservice Architecture.

## Technology Stack

- Service Discovery (Eureka)
- API Gateway (Zuul)
- Monitoring (Hystrix Dashboard)
- Security (OAuth2)
- Circuit Breaker (Hystrix)
- Client Side Load Balancing (Ribbon)
- Http Client (Feign)

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
|POST	| /user-service/administrative/followGroup/{*groupId*} | groupId |  | Follow Group  |  YES|
|DELETE	| /user-service/administrative/unFollowGroup/{*groupId*} | groupId |  | Unfollow Group  |  YES|
|GET	| /user-service/administrative/retrieveGroupIds/{*username*} | username |  | Retrieves following groups of users  |  YES|
|GET	| /user-service/homepage/retrieveTopStories?latitude={*latitude*}&longitude={*longitude*} | latitude&longitude |  | Retrieves Top Stories of following user and location  |  YES|
|GET	| /user-service/homepage/retrieveHotStories?latitude={*latitude*}&longitude={*longitude*} | latitude&longitude |  | Retrieves Hot Stories of following user and location  |  YES|
|GET	| /user-service/homepage/retrieveNewStories?latitude={*latitude*}&longitude={*longitude*} | latitude&longitude |  | Retrieves New Stories of following user and location  |  YES|
|GET	| /user-service/homepage/retrieveMyStories |  |  | Retrieves User stories  |  YES|





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
|POST	| /group-service/administrative/create |  | `GroupRequest` | Group creation  |  YES|
|GET	| /group-service/administrative/retrieveMyGroups |  |  | Returned groups moderated by user  |  YES|
|POST	| /group-service/administrative/update/{*GroupId*} | GroupId | `GroupRequest` | Update group (only by moderator)  |  YES|
|DELETE	| /group-service/administrative/delete/{*GroupId*} | GroupId |  | Remove group (only by moderator)  |  YES|
|GET	| /group-service/administrative/retrieve/{*GroupId*} | GroupId |  | Returned specific group  |  YES|
|GET	| /group-service/homepage/new/{*GroupId*} | GroupId |  | Returned stories (sorted by newest)  |  YES|
|GET	| /group-service/homepage/top/{*GroupId*} | GroupId |  | Returned stories (sorted by likes)  |  YES|
|GET	| /group-service/homepage/hot/{*GroupId*} | GroupId |  | Returned stories (sorted by comments)  |  YES|








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




