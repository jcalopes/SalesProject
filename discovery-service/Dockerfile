FROM openjdk:19-jdk-alpine
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/discovery-service.jar discovery-service.jar
EXPOSE 8761
ENTRYPOINT ["java","jar", "/discovery-service.jar"]