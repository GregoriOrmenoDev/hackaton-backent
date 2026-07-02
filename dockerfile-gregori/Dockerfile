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

# =====================================================================
# COMANDOS PARA SUBIR IMAGEN ACTUALIZADA AL DOCKER HUB
# Ejecutar desde la carpeta raiz hackaton-be/ en PowerShell:
#
# 1. Buildear:
#    docker build -f dockerfile-gregori/Dockerfile -t gregoriormenosanchez/hackaton-backend:latest .
#
# 2. Pushear:
#    docker push gregoriormenosanchez/hackaton-backend:latest
#
# 3. En EC2 actualizar:
#    docker pull gregoriormenosanchez/hackaton-backend:latest
#    docker compose -f docker-compose-be.yml up -d
# =====================================================================
