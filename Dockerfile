# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV MOVIE_DB_URL=jdbc:mysql://mysql:3306/movie_ticketing
ENV MOVIE_DB_USERNAME=admin
ENV MOVIE_DB_PASSWORD=admin
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
