FROM openjdk:17-buster

COPY target/*.zip grpc-java-server.zip

RUN unzip grpc-java-server.zip

ENTRYPOINT ["./grpc-java-server/bin/grpc-java-server"]
