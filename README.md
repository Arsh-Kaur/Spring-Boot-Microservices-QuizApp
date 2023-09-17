# Spring-Boot-Microservices-QuizApp

This is a scalable and maintanable quiz application based on microservices architecture. The project consists of 2 main microservices - quiz-microservice and question-microservice, where each webservice has its well-defined APIs.
- The **_quiz-service_** is responsible for handling the core functionalities of quiz taking process:
    - Request question service to create quiz with given category and numberofQuestions
    - Get the quiz from the database based on the quizId
    - Generate score for the quiz on submission
- The **_question-service_** is responsible for managing the question database.
    - It manages the questions including creating and retrieving them.
    - It generates the actual quiz upon request from quiz-service.
    - It calculates the score of the quiz as question-service is the one that has access to the questions database.
- We are using **_PostgreSQL_** as the database because it is a reliable choice for relational database and is well suited for application that require data persistence. To utilize it, PGAdmin4 has been used.
- The **_api-gateway_** service uses the **_Spring Cloud API gateway_** to route incoming requests to appropriate services based on the service discovery.
- All of these services rely on **_Eureka_** as a **_service registry_**, which serves the dual purpose of registering services and facilitating service discovery. Quiz-service, question-service and api-gateway instances register themselves with the Eureka server when they start up. The Eureka server keeps track of available instances for each service. API gateway can query Eureka to discover the available services.  
- Each of the microservice can be scaled individually based on the requirement. We are running two instances of question microservice and only one instance of quiz microservice. To run a new instance, copy the run configurations of the existing instance and mention the server port in the arguments section as : --server.port=8081
- The **_Spring Cloud LoadBalancer_** works automatically to automatically load balance requests to service instances. It provides a client-side load balancer that's aware of service instances registered with the service discovery server. It uses a load-balancing strategy to determine which instance to route a request to. We can check which service is called by printing the Environment.getProperty("local.server.port").
- Lombok has been used in all of the services to reduce the boilerplate code such as getters and setters for the models.

All different services are running on the below ports:
- Eureka : http://localhost:8761/
- Api-Gateway : http://localhost:8765/
- PostgreSQL : jdbc:postgresql://localhost:5432/
- Quiz Microservice : http://localhost:8090/
- Question Microservice instance 2: http://localhost:8080/ (default port)
- Question Microservice instance 2 : http://localhost:8080/ 

