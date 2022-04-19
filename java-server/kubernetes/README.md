# Kubernetes

This is the explaination how to deploy gRPC service on kubernetes.

## Setup with minikube (local)

- Install [minikube](https://formulae.brew.sh/formula/minikube)
- Install hyperkit driver

```sh
brew install hyperkit
```

### Deploy deployments and services

- [Build the app](../README.md)

- Start minikube or connect your kubectl to your cluster

```sh
minikube start --driver=hyperkit
```

- Deploy the service and deployment

```sh
kubectl apply -f java-server.service.yml -f java-server.deployment.yml
```

> This command will run minikube using hyperkit and not docker driver. Hyperkit will run in a VM and not in docker environement. That will be easier to expose deployment and services.

- Expose the service to your machine

With minikube you will need to expose the service in first terminal throw a tunnel.

```sh
minikube tunnel
```

In a second terminal you will need to get the vm url and ports with:

```sh
minikube service java-server-svc --url
```

You can now communicate with this service from your local application

## Run a client on kubernetes

- [Javascript client example](../../js-client/kubernetes/README.md)