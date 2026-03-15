FROM eclipse-temurin:21-jdk-jammy AS build
LABEL author="kishore"

WORKDIR /eureka

COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /eureka

COPY --from=build /eureka/target/*.jar eureka.jar

EXPOSE 8761

ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","eureka.jar"]