# FlightTrackingSystem
this is a flight tracking system for Amadeus travel to the future program

the project was developed using Java Spring Boot 3 and PostgreSQL

# Before starting the application you need to configure the database url (not required) , username and password(required) according to your database configuration

# The project has a JWT based authentication so you need to follow the following steps to be able to use the end points
1- send a post request to localhost:8080/api/v1/auth/register with the following body 
  ``` {
    "firstname": "admin",
    "lastname": "admin",
    "email": "admin@admin.com",
    "password": "12345",
    "role": "ADMIN"
}
  ```
