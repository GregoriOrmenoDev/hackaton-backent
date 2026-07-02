# ====== FASE DE CONSTRUCCION ======
FROM gregoriormenosanchez/maven:3.9-amazoncorretto-25-alpine AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ====== FASE DE EJECUCION ======
FROM gregoriormenosanchez/maven:3.9-amazoncorretto-25-alpine

RUN apk add --no-cache fontconfig ttf-dejavu

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
