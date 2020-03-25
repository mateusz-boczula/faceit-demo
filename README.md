# FACEIT code excercise - user microservice

### Overview
This piece of code is my (Mateusz Boczula) implementation of an excercise for creating an user microservice as a part of a larger microservices infrastructure. Please look at the *Java_Test_-_Write_a_small_Users_microservice.pdf* file for reference.

### How to run
Application has been created using the Spring Boot 2.2 and Gradle, so it is launchable by executing *gradlew bootRun* command from the root folder. There is no additional dependency required and the URI will be available on *http://localhost:8080*.

### User service
User service has been implemented as a rest endpoint with the following URIs:
- *GET /users/* - Fetches the list of all the users in the system. Additionaly *GET /users/?country=Poland* can be used to fetch only the resources matching the criteria. *userName, firstName, lastName and country* are available as criteria and they could be combined in the following way: *GET /users/?country=Poland&firstName=Mateusz*
- *POST /users/* - Creates a new user resource
- *PUT /users/{id}* - Updates an user resource with a given id
- *DELETE /users/{id}* - Deletes user resource with a given id

User creation and modification endpoints requires an user JSON with the following structure:
- *POST /users/*
```javascript
{
  "userName": "asdf",
  "password": "password",  
  "firstName": "Mateusz",  
  "lastName": "Boczula",  
  "email": "asdf@asdf.com",  
  "country": "Poland"
}
```
- *PUT /users/{id}*
```javascript
{
  "id": "1",
  "userName": "asdf",
  "password": "password",  
  "firstName": "Mateusz",  
  "lastName": "Boczula",  
  "email": "asdf@asdf.com",  
  "country": "Poland"
}
```

### Notifications
Notifying other services is a part of the criteria of the excercise. Here it has been implemented using webhooks which can be created as a REST resource within the system in the following way:
- *POST /webhooks/*
```javascript
{
  "actionType": "CREATE",
  "entityType": "User",  
  "postUrl": "http://localhost:8080/search/user/created"
}
```

actionType is an enum of *CREATE, UPDATE and DELETE* corresponding to the REST resource actions. Functionality has been implemented only for *User* resource, so entityType should be User and postUrl defines and endpoint which will be called when a given action has been executed by the User Service with User resource as a part of the body of the request. Here the functionality has been simplified to only call at the time of a REST action without acknowledgement from the receiving service, but for a real system such acknowledgement should be implemented. You can look at the *SearchService* within the codebase for given example and the user creation attempt should be logged into the console when the webhook given above is created first.

### Assumptions
- Database has been mocked as a Java map indexed by id stored in memory. The classes managing data have names ending with the *Repository* suffix.
- Health checks has been acquired through Spring Boot Actuator, and the following endpoints are available: *GET /actuator/health* and */actuator/info* (and a lot more can be enabled if required)
- Logging has beeen implemented though default SLFJ4 logging framework support with Spring Boot
- 