FROM openjdk:20-slim-buster

WORKDIR /app

COPY target/com.vladislav.logger.jar app.jar

CMD ["java", "-jar", "app.jar"]