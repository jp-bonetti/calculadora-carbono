# Calculadora de Carbono

Este projeto é um aplicativo backend para uma calculadora de carbono, construída usando Java e Spring Boot, para gerenciar usuários, atividades de emissão, categorias de emissão e calculo de emissões de carbono.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- JPA (Java Persistence API)
- JWT (JSON Web Token) para autenticação

## Estrutura do Projeto

- `controllers`: Contém os controladores REST para lidar com solicitações HTTP.
- `dtos`: Objetos de transferência de dados usados para transferir dados entre camadas.
- `entities`: Entidades JPA que representam as tabelas do banco de dados.
- `exceptions`: Exceções personalizadas usadas no aplicativo.
- `repositories`: Repositórios JPA para operações de banco de dados.
- `services`: Classes de serviço contendo lógica de negócios.
- `auth`: Serviço JWT para lidar com autenticação.
- `configs`: Classes de configuração.

## Swagger API

A documentação da API pode ser acessada em `http://localhost:8080/swagger-ui.html`.

## Endpoints

### Authenticação Endpoints

- **POST /auth/register**: Registra um novo usuário.
- **POST /auth/login**: Autentica um usuário e gera um token JWT.

### Users Endpoints

- **GET /api/users**: Obtem os detalhes do usuário autenticado.
- **DELETE /api/users**: Exclui o usuário autenticado.
- **PUT /api/users**: Atualiza os detalhes do usuário autenticado.

### Emission Activity Endpoints

- **GET /api/emissions/activities**: Obtem todas as atividades de emissão do usuário autenticado.
- **GET /api/emissions/activities/category/{categoryId}**: Obtem atividades de emissão para o usuário autenticado por categoria.
- **POST /api/emissions/category/{categoryId}/activities**: Adiciona uma nova atividade de emissão para o usuário autenticado.
- **DELETE /api/emissions/activities/{activityId}**: Exclui uma atividade de emissão para o usuário autenticado.
- **PUT /api/emissions/activities/{activityId}**: Atualiza uma atividade de emissão para o usuário autenticado.
- **GET /api/emissions/total**: Calcula as emissões totais do usuário autenticado.
- **GET /api/emissions/category/{categoryId}**: Calcula as emissões por categoria para o usuário autenticado.
- **GET /api/emissions/day**: Calcula as emissões por dia para o usuário autenticado.
- **GET /api/emissions/period**: Calcula as emissões de um período específico para o usuário autenticado.

### Emission Category Endpoints

- **GET /api/category**: Obtem todas as categorias de emissão do usuário autenticado.
- **POST /api/category**: Adiciona uma nova categoria de emissão no usuário autenticado.
- **DELETE /api/category/{categoryId}**: Exclui uma categoria de emissão do usuário autenticado.
- **PUT /api/category/{categoryId}**: Atualiza uma categoria de emissão do usuário autenticado.
- **GET /api/category/{categoryId}**: Obtem uma categoria de emissão por ID do usuário autenticado.


## Executando a aplicação

1. **Clone o repositório**:
    ```sh
    git clone https://github.com/bonetticrvg/calculadora-carbono.git
    cd calculadora-carbono
    ```

2. **Builde o projeto**:
    ```sh
    mvn clean install
    ```

3. **Execute a aplicação**:
    ```sh
    mvn spring-boot:run
    ```

## Inicialização pelo Docker

Para iniciar a aplicação usando Docker, siga os passos abaixo:

1. Certifique-se de que o Docker e o Docker Compose estão instalados em sua máquina.

2. Navegue até o diretório onde o arquivo `docker-compose.yml` está localizado.

3. Execute o comando abaixo para construir e iniciar os containers:
   ```sh
   docker-compose up --build

## Configuração

O aplicativo usa JWT para autenticação. Certifique-se de ter as configurações necessárias para JWT em seu arquivo `application.properties`.
