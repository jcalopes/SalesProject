FROM openjdk:11
MAINTAINER Joao Lopes <lopes.joao.ca@gmail.com>
ADD target/notification-service.jar notification-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar", "/notification-service.jar"]