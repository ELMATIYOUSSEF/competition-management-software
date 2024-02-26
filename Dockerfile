FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
ARG JAR_FILE=target/aftas.jar
ADD ${JAR_FILE} aftas
ENTRYPOINT ["java","-jar","aftas"]
