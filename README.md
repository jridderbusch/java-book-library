# java-book-library

Example project for Java with Quarkus, Hibernate, PostgreSQL, Docker, Swagger UI and OpenAPI.

## Information

- Dockerfile: [src/main/docker/Dockerfile.jvm](src/main/docker/Dockerfile.jvm)
- docker-compose.yml: [docker-compose.yml](docker-compose.yml)
  - For simplicity, the image is built in the docker-compose.yml, in this project
- `/swagger-ui` - Swagger UI
- `/openapi` - OpenAPI documentation
- `/api/v1/...` - All other API endpoints

## Building the application and run Docker Compose

```shell script
./gradlew build
docker-compose up
```

## Running the application in dev mode

  You can run your application in dev mode that enables live coding using:

```shell script
  ./gradlew quarkusDev
```

## Packaging and running the application

  The application can be packaged using:

```shell script
  ./gradlew build
```

