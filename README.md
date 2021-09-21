# HollandBarretteBank
Holland And Barrent Bank

## Description
Holland Barrette Bank is a production ready Spring boot starter kit application which contains functionality like retrieving customer details, 
retrieving customer account details and retrieving statements by given dates and creating transactions.

## Technology

- **Spring Boot**     - Server side framework
- **JPA**             - Entity framework
- **Lombok**          - Provides automated getter/setters
- **Swagger**         - In-built swagger documentation support
- **Junit**           - Unit testing framework
- **H2**              - H2 database embedded version

## Application Structure

## Running the server locally
The Bank application can be started using your favourite IDE and its run configuration support. If you are a terminal savvy, please use the following command -

````
mvn spring-boot:run
````
## Initial Data Load
There are three tables created and inserted initial data load for easy testing.
You can use the below swagger end points to add more transactions for testing 

## Swagger Documentation
Swagger documentation is in-built in this starter-kit and can be accessed at the following URL -
````
http://localhost:8080/swagger-ui.html
````


## Calling Starling Bank API
Starling Bank api is called in the endpoint http://localhost:8080/v1/starlingbank/balancestatement/67141534-3893-483c-8aff-fe4f3b6291a3
Please use the account number = 67141534-3893-483c-8aff-fe4f3b6291a3

## Unit test cases
There are multiple unit test cases written to cover the different components of the application.
````
mvn clean test  
````
 
