# Kubernetes

This is the explaination how to deploy gRPC service on kubernetes.

## Setup with minikube (local)

- Install [minikube](https://formulae.brew.sh/formula/minikube)

### Deploy deployments and services

- Deploy a gRPC server deployment and service like the [java-server](../../java-server/kubernetes/)

> Be sure your minikube is started or run `minikube start --driver=hyperkit`
> This command will run minikube using hyperkit and not docker driver. Hyperkit will run in a VM and not in docker environement. That will be easier to expose deployment and services.

- Deploy the envoy proxy from the [kubernetes folder](./)

```sh
kubectl apply -f envoy.deployment.yml -f envoy.service.yml
```

- Change the url to the gRPC server to the correct IP address in your [grpc-client.js](../webapp/src/grpc-client.js) (Need to point to your envoy proxy). Follow this instructions:

With minikube you will need to expose the service in first terminal throw a tunnel.

```sh
minikube tunnel
```

In a second terminal you will need to get the vm url and ports with:

```sh
minikube service js-client-envoy-svc --url
```

- Build your app with webpack the [webapp](../webapp/) folder

```sh
npx webpack src/grpc-client.js
```

- Build your webapp Dockerfile

```sh
docker build -t <tag of your container> .
```
> execute this command from the [webapp](../webapp/) folder.

- [Optional] Push the image on a registry

> Be sure to have the correct rights

- If you use a local image, you need to load your image into minikube VM.

```sh
minikube image load <tag of your container>
```

- Change the [js-client.deployment.yml](js-client.deployment.yml) to the correct image

> spec -> template -> containers -> js-client -> image

- Deploy the envoy and js-client deployments and services from [kubernetes folder](./)

```sh
kubectl apply -f js-client.deployment.yml -f js-client.service.yml
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
minikube service js-client-svc --url
```