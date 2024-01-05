#FROM gradle:8.5.0-jdk21 AS builder
#COPY build.gradle .
#COPY settings.gradle .
#COPY src ./src
#COPY config ./config
#RUN gradle clean build

FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./build/libs/grafana-test-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java","-jar","grafana-test-0.0.1-SNAPSHOT.jar"]