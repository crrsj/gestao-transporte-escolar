# --- ESTÁGIO 1: Compilação com Maven e Java 21 ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código fonte
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dá permissão de execução ao mvnw e compila o projeto ignorando testes
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# --- ESTÁGIO 2: Execução da Aplicação com JRE 21 ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o arquivo .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]