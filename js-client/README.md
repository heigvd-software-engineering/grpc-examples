# gRPC-WEB example

Js-client is a demo client to communicate with a gRPC server over gRPC-web.

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
> This will allow protoc to compile for gRPC web.

## Run

To run the grpc-web application you will need to follow these steps:

1) Have a [grpc server](../java/src//main/java/ch/heigvd/java/server/HelloWorldServer.java) running.

2) Install dependencies.

```sh
npm i
```

3) You need to compile your proto file:

- Your grpc objects:

```sh
protoc -I=./proto/ helloworld.proto \
  --js_out=import_style=commonjs:./webapp/src
```

- Your grpc stub:

```sh
protoc -I=./proto/ helloworld.proto \
  --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./webapp/src
```

> `-I` is the option to specify the folder where your proto is stored.

After that you can use the interface ([hellowold_grpc_web_pb.js](./webapp/src/helloworld_grpc_web_pb.js) and [helloworld_pb.js](./webapp/src/helloworld_pb.js)) generated in the src folder in the [grpc-client.js](./webapp/src/grpc-client.js).

> Don't forgot to export the function you want to call in your [index.html](./webapp/index.html).

> Every time you change the proto file you will need to redo this step.

You will need to bundle your code with webpack to be able to use it in the index.html.


3) Bundle your js files to be used in your index.html.

```sh
cd webapp
npx webpack src/grpc-client.js
cd ..
```

> This will bundle your js file in a grpc.js file in the lib folder.

4) Run an [envoy proxy](./envoy/envoy.yaml) (check the configuration match your server ports).

```sh
envoy -c ./envoy/envoy.yaml
```

5) Open the [index.html](./webapp/index.html) in your browser.

## Deployment on kubernetes

Follow this [documentation](../kubernetes/README.md).

## Sources

- [gRPC offical website](https://grpc.io/docs/platforms/web/quickstart/).