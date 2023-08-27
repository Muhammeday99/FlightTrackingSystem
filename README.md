# FlightTrackingSystem
this is a flight tracking system for Amadeus travel to the future program

the project was developed using Java Spring Boot 3 and PostgreSQL

# Before starting the application you need to configure the database url (not required) , username and password(required) according to your database configuration

# NOTE: If you don't want to worry about authentication you can check out the no-auth branch there you can try all the endpoints without having to use a JWT token

# The project has a JWT based authentication so you need to follow the following steps to be able to use the end points
1- send a post request to localhost:8080/api/v1/auth/register with the following body 
  ```
{
    "firstname": "admin",
    "lastname": "admin",
    "email": "admin@admin.com",
    "password": "12345",
    "role": "ADMIN"
}
  ```
this will give you an token that you can use to use the endpoints

2- when calling an endpoint using postman make sure to set the Authorization to type "Bearer token" and paste the token there

# Endpoints
to see the full endpoints documentation start the application and on your browser navigate to **http://localhost:8080/swagger-ui/index.html#/** 
