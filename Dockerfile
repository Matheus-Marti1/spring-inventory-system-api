FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew clean build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

RUN apk add --no-cache ca-certificates && update-ca-certificates

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]