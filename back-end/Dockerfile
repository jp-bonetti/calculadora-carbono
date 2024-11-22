FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN ./mvnw clean install

EXPOSE 8080

CMD ["java", "-jar", "target/back-end-0.0.1-SNAPSHOT.jar"]