# gRPC Java example

Java-server is a demo of a server implementation of gRPC in Java.

## Prerequisite

- Java 17
- Maven
- [protoc cli](https://github.com/protocolbuffers/protobuf)

## Compile gRPC proto file

The grpc maven plugin is configured to generate the code when you do a:
```sh
mvn clean install
```

> The generated code will be found in the `target/protobuf` folder.
> If you are using an IDE like Intellij, you will probably need to set this folder as a source folder to have autocompletion.

## Run the app

The `mvn clean install` command generates a zip folder in your target folder.
Unzip the zip folder:

```sh
unzip target/grpc-java-server.zip
```

Run the script to run the app:

```sh
./target/grpc-java-server/bin/grpc-java-server
```

> If you are on Windows, we suggest you to use [WSL](https://docs.microsoft.com/en-us/windows/wsl/about).

You should see:
```sh
avr. 12, 2022 12:05:28 PM ch.heigvd.java.client.HelloWorldServer start
INFO: Server started, listening on 9090
```

You have now a grpc server running!

> You are not able to communicate with this server without a client.
> You can use the client available here [js-client](../js-client).
> Note: Postman now support grpc to do some testing.

## Deployment on kubernetes

Follow this [documentation](../kubernetes/README.md).

## Sources

- [gRPC offical website](https://grpc.io/docs/languages/java/).
