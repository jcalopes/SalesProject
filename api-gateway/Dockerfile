FROM openjdk:19-jdk-alpine
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/api-gateway.jar api-gateway.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar", "/api-gateway.jar"]