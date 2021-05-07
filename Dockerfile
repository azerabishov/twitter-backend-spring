FROM openjdk:13-jdk-alpine
WORKDIR /app
COPY ./target/app.jar /app
EXPOSE 9090
CMD ["java", "-jar", "app.jar"]