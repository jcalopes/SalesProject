SalesProject

Microservice-based application to manage orders, products and the underlying inventory. Implements well-known design patterns regarding microservices ecossystem to build a reliable and maintainable system as it grows. So you can find:
- The service discovery pattern implemented by Eureka Service Discovery as well as the LoadBalancer to forward together the incoming requests to the target host when multiple instances of the same service are running. 
- An API Gateway Pattern was also implemented placed in front of the microservices acting as the only entrypoint to the application. Tasks as authentication and forwarding are performed by the API preventing the requests hit directly in the microservices. 
- The Circuit Breaker Pattern was also implemented to prevent possible unavailable services make the application getting stuck and escalate the issues. Resilience4j was used to apply the pattern to foster the "Fail Fast" concept.

As the testing plays an important role to ensure that new integrations aren't actually  previous features and grant that everything works as expected some integration tests were performed using JUnit and testContainers libraries to mock the databases used in "production".
The project is on going so expect more features and other conecpts being covered soon as such CI/CD and kubernetes.
