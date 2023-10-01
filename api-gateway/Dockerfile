FROM openjdk:11
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/api-gateway.jar api-gateway.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar", "/api-gateway.jar"]