FROM openjdk:11
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/discovery-service.jar discovery-service.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar", "/discovery-service.jar"]