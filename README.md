[![Build Status](https://travis-ci.org/nicolasmanic/Facegram.svg?branch=master)](https://travis-ci.org/nicolasmanic/Facegram)
[![codecov](https://codecov.io/gh/nicolasmanic/Facegram/branch/master/graph/badge.svg)](https://codecov.io/gh/nicolasmanic/Facegram)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nicolasmanic/Facegram/blob/master/LICENCE)
# Facegram

**A location based social network**

This is a PoC application, which demonstrates how Spring Boot, Spring Cloud (and the Netflix stack) can be used in a 
Microservice Architecture.

## Technology Stack

### Discovery server (Eureka)

### Edge server (Zuul)

### Monitoring (Hystrix Dashboard)

### Security (OAuth2)

### Http Client, Circuit Breaker

## Topology

## API

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

Todo:
- [ ] Add automation script
- [ ] Build docker images

## Feedback

For any feedback please contact me with [linkedin](https://www.linkedin.com/in/nick-kanakis-24b34677)




