FROM eclipse-temurin:17.0.2_8-jre-alpine

COPY target/*.zip grpc-java-server.zip

RUN unzip grpc-java-server.zip

ENTRYPOINT ["./grpc-java-server/bin/grpc-java-server"]