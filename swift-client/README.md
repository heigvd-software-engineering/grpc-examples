# Swift client

> Caution: gRPC is not officially supported for the moment.

Swift-client is a demo client to communicate with a gRPC server in python.

## Prerequisite

- xCode
- protoc

## App structure

- The iOS and MacOS app is in the [swift-app](./swift-app/) repository.
It will use the [swift-grpc](./swift-grpc/) package to communicate with grpc.

- The proto file and the implementation of the client communicator is in the [swift-grpc/Sources/HelloWorld/Model/](./swift-grpc/Sources/HelloWorld/Model/)

- The configuration of the package is in the [Package.swift](./swift-grpc/Package.swift) file.


## Setup 

### How to use swift-grpc package from swift-app

- Open swift-app in xCode.

- Add swift-grpc as a dependency.

  - Click on swift-app in the left menu to access the `app settings`.
  - Click on the Package dependencies tab.
  - Click on the `+`button.
  - Click on `Add Local`.
  - Select the [swift-grpc](./swift-grpc/) folder
  - You should now see in the Packages folder in the left menu your `swift-grpc` package.
  - Go back in the `app settings`.
  - Click on the `targets` that will need to use this package.
  - In the `General` tab, you need to add a new `Framewords, Libraries, and Embedded Content`.
  - Select in your `swift-grpc` package the `HelloWorldClient` library.

You can now run your app from xCode!

## Generate the grpc files with proto

### Plugins 

You will need to have the `swift-protobuf` and `grpc-swift` protoc plugins.

They are available on brew (MacOS only):

```sh
brew install swift-protobuf grpc-swift
```

> For other OS follow the instruction [here](https://github.com/grpc/grpc-swift#getting-the-protoc-plugins)

> The plugins name are not the same if you install it via brew or throw the source link so for the next steps be aware to change with the name of the plugins you installed

### Compile

For compiling the swift-protobuf files execute in your terminal ([from source directory](.)):

```sh
protoc \
  --proto_path=./swift-grpc/Sources/HelloWorld/Model \
  --plugin=swift-protobuf \
  --swift_opt=Visibility=Public \
  --swift_out=./swift-grpc/Sources/HelloWorld/Model \
  ./swift-grpc/Sources/HelloWorld/Model/helloworld.proto
```

For compiling the swift-grpc files execute in your terminal ([from source directory](.)):

```sh
protoc \
  --proto_path=./swift-grpc/Sources/HelloWorld/Model \
  --plugin=swift-grpc \
  --grpc-swift_opt=Visibility=Public \
  --grpc-swift_out=./swift-grpc/Sources/HelloWorld/Model \
  ./swift-grpc/Sources/HelloWorld/Model/helloworld.proto
```

You can now modified your [helloWorldClientGRPC](./swift-grpc/Sources/HelloWorld/Client/helloWorldClientGRPC.swift) with the new spec you have described in your [protoc file](./swift-grpc/Sources/HelloWorld/Model/helloworld.proto).

## Documentation

- [https://github.com/grpc/grpc-swift](https://github.com/grpc/grpc-swift)