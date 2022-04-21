# Python client

Python-client is a demo client to communicate with a gRPC server in python.

## Prerequisite

- Python3
- pip
- grpcio

```sh
python -m pip install grpcio
```

- gRPC tools

```sh
python -m pip install grpcio-tools
```

> Python’s gRPC tools include the protocol buffer compiler protoc and the special plugin for generating server and client code from .proto service definitions.

## Compile

- Compile the proto file

```sh
python -m grpc_tools.protoc -I.  --python_out=. --grpc_python_out=. helloworld.proto
```

## Run

- Start a gRPC server like the [java-server](../java-server/)

- Run the python client

```sh
python helloWorldClient.py
```