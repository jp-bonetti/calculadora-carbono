FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY /mvnw .

COPY . .

RUN ./mvnw clean install

EXPOSE 8080

CMD ["java", "-jar", "target/back-end-0.0.1-SNAPSHOT.jar"]