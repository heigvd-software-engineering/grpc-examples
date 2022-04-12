# gRPC Java example

## Prerequisite

- Java 17
- [protoc cli](https://github.com/protocolbuffers/protobuf) - to edit
- Maven

## Compile gRPC proto file

The grpc maven plugin is configured to generate the code when you do a:
```sh
mvn clean install
```

> The generated code will be found in the `target/protobuf` folder.
> If you are using an IDE like Intellij, you will probably need to set this folder as a source folder to have autocompletion.

## Run the app

You can run the app in your favorite IDE or you can run the compile application.

The `mvn clean install` generate a zip folder in your target folder.
Unzip the zip folder:

```sh
unzip target/grpc-java-server.zip
```

Run the script to run the app:
For MacOs/Linux:

```sh
./target/grpc-java-server/bin/grpc-java-server
```

For Windows:
```sh
bat ./target/grpc-java-server/bin/grpc-java-server.bat
```

You should see:
```sh
avr. 12, 2022 12:05:28 PM ch.heigvd.java.server.HelloWorldServer start
INFO: Server started, listening on 9090
```

You have now a grpc server running!

> You are not able to communicate with this server without a client.
> You can now setup a client like the [js-client](../js-client)
> Note: Postman now support grpc to do some testing

## Sources

- [gRPC offical website](https://grpc.io/docs/languages/java/)
