# Cisco assignment

#### Dependencies
- Appliation uses MongoDB to persist arbitrary object.
- Application uses junit, mockito for testing

#### Build
- Run ```mvn clean install``` to build the project

## Run
mvn spring-boot:run

#### Run
- Run ```mvn spring-boot:run```
- Above command starts the application on localhost:8080

#### APIs
- `POST api/objects` : Creates new arbitrary object. Contents of the object are passed as Json.
- `GET api/objects/{uid}` : Retrievs arbitrary object whoses uid is passed as path variable.
- `GET api/objects/` : Retrievs all arbitray object.
- `PUT api/objects/{uid}` : updates arbitrary object whoses uid is passed as path variable. New contents of object are passed as Json.
- `DELETE api/objects/{uid}` : deletes arbitrary object whoses uid is passed as path variable.

## Project details:
REST API that supports basic create, read, update, delete operations on arbitrary JSON objects. 
When an object is created, it's assigned a unique identifier. All subsequent operations (read, update, delete) are performed using this unique identifier.
As the contents of arbitrary object is not fixed, used NoSQL to persist arbitray object to database.
Used MongoRepository to interact database form application.
PUT and DELETE are idempotent apis.

