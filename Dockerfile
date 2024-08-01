FROM openjdk:17-alpine
WORKDIR /app
COPY ./build/libs/board-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java", "-jar", "/app/app.war"]
