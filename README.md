# SalesProject

**Microservice-based application to manage orders, products and underlying inventory.**

### Instalation
Working on that.

## What to expect?

### Architectural Concepts:

Implements well-known design patterns regarding microservices ecosystem to build a reliable and maintainable system as it grows. So you can find:

- The **Service Discovery Pattern** implemented by Eureka Service Discovery as well as the LoadBalancer to forward together the incoming requests to the target host when multiple instances of the same service are running.
- An **API Gateway Pattern** was also implemented placed in front of the microservices acting as the only entrypoint to the application. Tasks as authentication and forwarding are performed by the API preventing the requests hit directly in the microservices.
- The **Circuit Breaker Pattern** was also implemented to prevent possible unavailable services make the application getting stuck and escalate the issues. Resilience4j was used to apply the pattern to foster the "Fail Fast" concept.

### Security Concepts:

Security is increasingly more in demand today as "open doors" are being exploited by malicious actors every time. So implementing the best/latest security standards is a must when it comes building an application.
Aware of it the API Gateway is protected using **OAuth 2.0** Standard relying on KeyClouk Library granting access to the resources only by authorized users.

### Quality Assurance Concepts:

As the testing plays an important role to ensure that new integrations aren't actually previous features and grant that everything works as expected some **Integration Tests** were performed using JUnit and testContainers libraries to mock the databases used in "production".

### CI/CD Concepts:

Still not implemented but expecting more features soon such as Jenkins pipeline and kubernetes.

**Stay tuned!**
