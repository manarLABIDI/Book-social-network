
#build stage

FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime stage

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/target/book-networks-*.jar /app/

EXPOSE 8088


