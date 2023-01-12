FROM openjdk:17-alpine

EXPOSE 8080

ARG DB_USER
ARG DB_PASS
ARG DB_URL

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-practice.jar
ENTRYPOINT ["java","-jar","/payment-practice.jar"]