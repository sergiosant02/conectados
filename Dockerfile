# Etapa de construcción
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo en el contenedor
WORKDIR /conectadosBack

# Copia el pom.xml y los archivos del proyecto al contenedor
COPY /conectadosBack/pom.xml .
COPY /conectadosBack/src ./src

# Ejecuta el comando mvn clean package para construir la aplicación
RUN mvn clean package -DskipTests

# Etapa de producción
FROM openjdk:17-oracle

# Establece el directorio de trabajo en el contenedor
WORKDIR /conectadosBack

# Copia el archivo JAR generado desde la etapa de construcción al contenedor
COPY --from=build /conectadosBack/target/conectados-0.0.1-SNAPSHOT.jar .

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "conectados-0.0.1-SNAPSHOT.jar"]