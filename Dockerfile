# Build the application inside the image so hosted builds do not depend on a local target directory.
FROM eclipse-temurin:23-jdk AS build
WORKDIR /workspace
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src/ src/
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:23-jre
WORKDIR /app
EXPOSE 8080
COPY --from=build /workspace/target/demo-0.0.7-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
