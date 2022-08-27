FROM openjdk:19-jdk-alpine
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/notification-service.jar notification-service.jar
EXPOSE 8085
ENTRYPOINT ["java","jar", "/notification-service.jar"]