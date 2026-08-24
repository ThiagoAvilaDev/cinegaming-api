FROM eclipse-temurin:25-jdk

LABEL authors="ThiagoAvilaDev"

WORKDIR /app

COPY target/*.jar cinegaming_api.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","cinegaming_api.jar"]