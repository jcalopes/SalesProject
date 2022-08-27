FROM openjdk:19-jdk-alpine
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/product-service.jar product-service.jar
EXPOSE 8081
ENTRYPOINT ["java","jar", "/product-service.jar"]