FROM gradle:8.12.0-jdk23-alpine AS build
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon
COPY src/main/resources/db/migration .

RUN chmod +x gradlew

RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

