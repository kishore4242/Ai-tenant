FROM eclipse-temurin:21-jdk-jammy AS build
LABEL author="kishore"

WORKDIR /gateway

COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /gateway

COPY --from=build /gateway/target/*.jar gateway.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","gateway.jar"]