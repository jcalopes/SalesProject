FROM openjdk:11
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/order-service.jar order-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/order-service.jar"]