FROM maven:3.9.12-eclipse-temurin-21 AS build
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Builder l'application
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8088

# Variables d'environnement par défaut
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8088

# Point d'entrée
ENTRYPOINT ["java", "-jar", "app.jar"]