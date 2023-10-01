FROM openjdk:11
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/inventory-service.jar inventory-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar", "/inventory-service.jar"]