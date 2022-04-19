# Kubernetes

This is the explaination how to deploy gRPC service on kubernetes.

## Setup with minikube (local)

- Install [minikube](https://formulae.brew.sh/formula/minikube)

### Deploy deployments and services

- Deploy a gRPC server deployment and service like the [java-server](../../java-server/kubernetes/)

> Be sure your minikube is started or run `minikube start --driver=hyperkit`
> This command will run minikube using hyperkit and not docker driver. Hyperkit will run in a VM and not in docker environement. That will be easier to expose deployment and services.

- Change the url to the gRPC server to the correct IP address in your [grpc-client.js](../webapp/src/grpc-client.js)

With minikube you will need to expose the service in first terminal throw a tunnel.

```sh
minikube tunnel
```

In a second terminal you will need to get the vm url and ports with:

```sh
minikube service java-sever-svc --url
```
> Change java-server-svc with the correct server service name

- Build your app with webpack

```sh
npx webpack src/grpc-client.js
```
> execute this command from the [webapp](../webapp/) folder.

- Build your webapp Dockerfile

```sh
docker build -t <tag of your container> .
```
> execute this command from the [webapp](../webapp/) folder.

- [Optional] Push the image on a registry

> Be sure to have the correct rights

- Change the [js-client.deployment.yml](js-client.deployment.yml) to the correct image

> spec -> template -> containers -> js-client -> image

- Deploy the envoy and js-client deployments and services

```sh
kubectl apply -f envoy.deployment.yml -f envoy.service.yml -f js-client.deployment.yml -f js-client.service.yml
```

You should see your deployments, pods and services now.

```sh
kubectl get deployments
```

```sh
kubectl get pods
```

```sh
kubectl get services
```

- Expose your service with minikube

```sh
minikube tunnel
```

You can get the js-client-svc ip address with:

```sh
minikube service js-clinet-svc --url
```