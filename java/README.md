# GRPC Java example

## prerequisite

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
