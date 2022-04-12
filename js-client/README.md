# gRPC-WEB example

## Prerequisite

- [envoy cli](https://www.envoyproxy.io/docs/envoy/latest/start/install)
- [protoc cli](https://github.com/protocolbuffers/protobuf)
- [npm](https://docs.npmjs.com/cli/v7/configuring-npm/install)
- [make](https://www.w3schools.in/cplusplus/install)

## Install gRPC web plugin

Compile the plugin.

```sh
cd ./plugin
make install-plugin
cd ..
```
> This will allow protoc to compile for gRPC web

## Compile

You will need to compile the [proto](./proto/) files.

1) your grpc objects
```sh
protoc -I=./proto/ helloworld.proto \
  --js_out=import_style=commonjs:./webapp/src
```

2) your grpc stub
```sh
protoc -I=./proto/ helloworld.proto \
  --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./webapp/src
```

> `-I` is the option to specify the folder where your proto is stored.

## Run

To run the grpc-web application you will need to follow this steps:

1) Have a [grpc server](../java/src//main/java/ch/heigvd/java/server/HelloWorldServer.java) running
2) Run an [envoy proxy](./envoy/envoy.yaml) (check the configuration match your server ports etc)
```sh
envoy -c ../envoy/envoy.yaml
```
3) open the [index.html](./webapp/index.html) in your browser.

## To edit

### Proto change

If you change your proto definition, you will need to change it also in your server.

You need to recompile your proto:
1) your grpc objects
```sh
protoc -I=./proto/ helloworld.proto \
  --js_out=import_style=commonjs:./src
```

2) your grpc stub
```sh
protoc -I=./proto/ helloworld.proto \
  --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./src
```

After you can use the interface ([hellowold_grpc_web_pb.js](./webapp/src/helloworld_grpc_web_pb.js) and [helloworld_pb.js](./webapp/src/helloworld_pb.js)) generated in the src folder in the [grpc-client.js](./webapp/src/grpc-client.js).
> Don't forgot to export the function you want to call in your [index.html](./webapp/index.html).

You will need to bundle your code with webpack to be able to use it in the index.html.

```sh
npx webpack src/grpc-client.js
```

Your application is ready!

## Sources

- [gRPC offical website](https://grpc.io/docs/platforms/web/quickstart/)