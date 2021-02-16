## Rate Management System

# Microservice: rates-service
This is a demo microservice for saving,updating and searching of rates.

### Tech stack used


1. Java 8
2. Spring Boot 2.4.1
3. Spring REST API (Spring Web)
4. Lombok 
5. Spring Data JPA & H2 in-memory database (exposed the client dashboard to see the records)
6. springdoc-openapi-ui - Auto generated API documentation using OpenAPI 3.0 and exposed Swagger UI for the same (Refer the link at the bottom of this page)
7. TDD approach using JUnit 5, Mockito, and Spring Boot Test
8. jacoco-mavn-plugin for code coverage
9. Spring boot starter validation for request validation  
10. Spring security validation and role base authentication.
11.Call downstream services using webclient
12.Actuator endpoints enabled using spring-boot-starter-actuator 
13. Docker file for deploying

-----

## Docker details


docker build -t rates .

docker run --name rates -p8080:8080 -d rates

docker ps -a
docker logs rates

-----

## Testing the API
 
The API can be called using Basic auth to the endpoints.

User1 credentials : user1/letmein

Admin credentials : admin/letmein


Admin can create,update,view  and delete rates
User can only search rates.403 Forbidden error for user if he tries to execute the admin APIs.



--------------
## Attachments


Actuator endpoints are exposed - refer the snapshots

Swagger endpoints are exposed - snapshots for reference

h2-console can be checked

The application is checked using docker container

The circuit breaker is added

logback used for logging


