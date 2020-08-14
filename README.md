# HeyCar Coding Challenge 
| Author | Cengizhan Köse | 

## How to Build
1. `./gradlew clean build` command:
    * compiles sources
    * does checkstyle (aka linting)
    * docker-compose
    * flyway migrates
    * applies reverse engineering with jooq (aka codegen)
    * unit and integrationTest run
    * bootJars

## How to Run
On project directory, running;
1. `./gradlew clean build` builds the jar file
2. `docker-compose up -d --build` spins up a postgres db and the app itself after

(Of course, as an alternative approach,after running postgres instance manually, 
`./gradlew bootRun` command may run)

## API 
http://localhost:8080/swagger-ui.html

## Libraries&Tools
* Spring boot
* Spring rest
* Java 11
* Lombok
* Rest Assured
* Junit and Mockito
* Docker
* Postgres
* jooq

## Problems
1. `/vehicle-listings` url for batch insert is not the best way due to
misleading behaviour: It sounds like an api to create one and only one resource
regarding RESTful specifications. When to return `200`, when all of them succeeds?
Or will the response still be `200` with a response body including successful resource urls?
The correct approach would be to create/generate a new extended resource like 
`/vehicle-listing-collection` and consider it as a `database transaction` by validating each
element. Therefore, naming convention kept as requested and will return `200` if only
all resources are valid.

2. The challenge does not require an `Authentication` mechanism therefore it was faked in 
a way like dealer ids are being injected into the request header in a hypothetical gateway.

3. Also, it might be better to use `GET /vehicle-listings` endpoint with query parameters rather than
adding `/search` prefix. (therefore, there is no such endpoint like `/search` but `GET /vehicle-listings`)
     
## Future Ideas / Missing
1. Domain exceptions are really important when it comes to return appropriate error responses to 
client. Therefore instead of throwing bare Runtime exceptions, it would be better to define domain
exceptions and manage/convert them into error responses with error handlers.

2. Api first approach is really handy when it comes to parallelize work between the server and client side
(maybe also qa) and remove dependencies among all parties. Therefore, a better approach would be defining
api yaml files, merge them and create openapi spec rather than leaving all functionality to builtin
library.

3. Vavr library might be introduced. (Beside eliminating side effects in order to
create pure functions, it also provides a good / extended `Collections` library)