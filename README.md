**Microservice-based application to manage orders, products and underlying inventory.**

### Installation (2 Ways)
1) For run the application properly you can run each microservice independently and configure a Mysqlserver for order-service and also a use Mongo Atlas or a Mongo locally for inventory-service and product-service to ensure data persistence.
   You will also need to have a **Kafka Server** running and mention it in notification-service properties and in order-service as well.

2) Use **Mongo Atlas** or a Mongo locally for inventory-service and product-service and run command below:

                        docker compose up Deployment/docker-compose.yml

## What to expect?

### Architectural Concepts:

Implements well-known design patterns regarding microservices ecosystem to build a reliable and maintainable system as it grows. So you can find:

- The **Service Discovery Pattern** implemented by Eureka Service Discovery as well as the LoadBalancer to forward together the incoming requests to the target host when multiple instances of the same service are running.
- An **API Gateway Pattern** was also implemented placed in front of the microservices acting as the only entrypoint to the application. Tasks as authentication and forwarding are performed by the API preventing the requests hit directly in the microservices.
- The **Circuit Breaker Pattern** was also implemented to prevent possible unavailable services make the application getting stuck and escalate the issues. **Resilience4j** was used to apply the pattern to apply the "Fail Fast" concept.

### Event-Driven Concepts:
There are some ways to communicate between services and **Messaging Pattern** is one of them. Thus to comunicate with notification-service was implemented asynchronous communication using a widely used message queue - **RabbitMQ**. Notification-service acts as a consumer having the order-service the producer on the other side.

### Security Concepts:
Security is increasingly more in demand today as "open doors" are being exploited by malicious actors every time. So implementing the best/latest security standards is a must when it comes building an application.
Aware of it the API Gateway is protected using **OAuth 2.0** Standard relying on KeyClouk Library granting access to the resources only by authorized users.

### Quality Assurance Concepts:
As the testing plays an important role to ensure that new integrations aren't actually breaking the previous features as well as ensuring that everything is working as expected some **Integration Tests** were performed using JUnit and testContainers libraries to mock the databases used in "production".

### CI/CD Concepts:
In microservices architecture each microservice should be managed independently. Thus, each microservice have configured a Jenkins pipeline to automate some tasks and ensuring that a new integration will not compromise the current codebase.
The pipeline performs a set of stages as build, test, deploy to grant continuous integration and continuous deployment as well. Once the pipeline runs successfully which means that a new integration passes the previous tests and also the tests that eventually were designed during the commit and new image has been updated in docker hub repository.
The CI/CD is the main concept behind DevOps and make the development process smoother and more reliable.

### Communication Patterns:
In microservices architecture implies that exist some communication between the services. This communication could happen in two different paradigms: asynchronous and synchronous.
Synchronous can be useful in some scenarios for its simplicity however we have to keep in mind that the user experience could be affected as the sender have blocked waiting for the response thus we need both parts available to happen the communication. To prevent issues in this situation we implemented aforementioned pattern - Circuit Breaker.
On the other hand we have asynchronous communication which it's widely used nowadays. This kind of communication means that the sender sends a message to the recipient and continues doing other work without being blocked waiting for the response. We use Kafka which uses asynchronous communication to send messages from the order-service to notification-service.

### Distributed Tracing:
In a production environment an application can receive thousands of request per short periods, so It's important to implement mechanisms to trace the whole request lifecycle. So it's implemented using Sleuth and Zipkin for UI to help query the logs to make easier find out the root causes in abnormal scenarios.   

### Monitoring
We should implement monitoring tools as soon as possible to predict problems and unpleasant situations avoiding them to be perceived by the clients. So Spring provides some features to help in this task thus we use actuator as a source of useful data and metrics regarding processing and memory consumption as well as other interesting statistics.
In the end we have a dashboard monitoring the state of our services almost in real time to ensure that everything is working as expected.
Then we will use **Prometheus** to fetch this data from **Actuator** endpoints. Once we have the data we use **Grafana** which provide a power set of visualising tools to help delivery the information from Prometheus in a more interactive and friendly way.
**Stay tuned!**
