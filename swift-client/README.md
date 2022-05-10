# Swift client

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