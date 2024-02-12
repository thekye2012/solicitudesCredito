# Establece la imagen base con Java 17
FROM openjdk:17-jdk-slim AS build

# Argumento para especificar el archivo JAR
ARG JAR_FILE=target/*.jar

# Copia el archivo .jar construido por Maven en el contenedor
COPY ${JAR_FILE} app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]
