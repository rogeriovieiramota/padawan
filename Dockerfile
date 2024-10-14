# Usar uma imagem base do Maven com JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build
RUN echo "A imagem será do Maven, versão 3.8.7, com o JDK 17 do Temurin dist. de Java OpenJDK ..."

# Definir o diretório de trabalho dentro do container
RUN echo "Definindo o diretório /app dentro do container como o diretório de trabalho ..."
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
RUN echo "Copia do pom.xml do diretório atual/local para o diretório de trabalho no container (/app) ..."
COPY pom.xml .

# Download das dependências do projeto, sem executar o build ainda
RUN echo "Baixando as dependências do Maven do pom.xlm do projeto ..."
RUN mvn dependency:go-offline

# Copiar todo o código-fonte do projeto \
RUN echo "Copiando todo o conteúdo do diretório local (código-fonte, arquivos de configuração, etc.) para o diretório do container (/app). ..."
COPY . .

# Executar o build da aplicação usando o Maven
RUN echo "Compilando o código-fonte do projeto, empacotando no arquivo .jar ..."
RUN mvn clean package -DskipTests

# Usar uma imagem base mais leve para rodar o JAR gerado
RUN echo "Construção, baseado imagem mais leve, que usa o JDK 17 e é baseada no Alpine Linux, usada para rodar a aplicação, reduzindo o tamanho final da imagem. ..."
FROM eclipse-temurin:17-jdk-alpine

# Definir o diretório de trabalho dentro do container
RUN echo "Definindo o diretório de trabalho dentro do container ..."
WORKDIR /app

# Copiar o JAR gerado no estágio anterior para o container final
RUN echo "Copia o iniciante-0.0.1-SNAPSHOT.jar gerado no build para o diretório de trabalho no container final, nomeando-o como app.jar ..."
COPY --from=build /app/target/iniciante-0.0.1-SNAPSHOT.jar app.jar

# Definir o comando de inicialização da aplicação
RUN echo "Definindo o comando que será executado quando o container iniciado, executará o java -jar app.jar para iniciar a aplicação. ..."
ENTRYPOINT ["java", "-jar", "app.jar"]

# Definir a porta exposta pela aplicação
RUN echo "aplicação no container utilizará a porta 8080 da aplicação. ..."
EXPOSE 8080
