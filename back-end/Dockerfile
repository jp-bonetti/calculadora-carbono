# Use uma imagem oficial do OpenJDK como imagem base
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie os arquivos do projeto para o container
COPY . .

# Construa a aplicação
RUN ./mvnw clean install

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Execute a aplicação
CMD ["java", "-jar", "target/back-end-0.0.1-SNAPSHOT.jar"]