# SalesProject

**Microservice-based application to manage orders, products and underlying inventory.**

### Instalation
Work in progress.

## What to expect?

### Architectural Concepts:

Implements well-known design patterns regarding microservices ecosystem to build a reliable and maintainable system as it grows. So you can find:

- The **Service Discovery Pattern** implemented by Eureka Service Discovery as well as the LoadBalancer to forward together the incoming requests to the target host when multiple instances of the same service are running.
- An **API Gateway Pattern** was also implemented placed in front of the microservices acting as the only entrypoint to the application. Tasks as authentication and forwarding are performed by the API preventing the requests hit directly in the microservices.
- The **Circuit Breaker Pattern** was also implemented to prevent possible unavailable services make the application getting stuck and escalate the issues. Resilience4j was used to apply the pattern to apply the "Fail Fast" concept.

### Event-Driven Concepts:

There are some ways to comunicate between services and **Messaging Pattern** is one of them. Thus to comunicate with notification-service was implemented asyncronous comunication using a widely used message broker - **Kafka**. Notification-service acts as a consumer having the order-service the producer on the other side.

### Security Concepts:

Security is increasingly more in demand today as "open doors" are being exploited by malicious actors every time. So implementing the best/latest security standards is a must when it comes building an application.
Aware of it the API Gateway is protected using **OAuth 2.0** Standard relying on KeyClouk Library granting access to the resources only by authorized users.

### Quality Assurance Concepts:

As the testing plays an important role to ensure that new integrations aren't actually breaking the previous features as well as ensuring that everything is working as expected some **Integration Tests** were performed using JUnit and testContainers libraries to mock the databases used in "production".

### CI/CD Concepts:

Work in progress.

### Distributed Tracing:
In a production environment an application could receive thousands of request per shot periods so its important to implement mechanisms to trace the whole request lifecycle. So it's implemented using Sleuth and Zipkin for UI to help query the logs to make easier find out the root causes in abnormal scenarios.   


**Stay tuned!**
