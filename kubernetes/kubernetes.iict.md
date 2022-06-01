# Kubernetes IICT

How to config kubectl to connect to the iict kubernetes cluster.

## Config kubectl

> You should be on the HEIG network or connected with the VPN.

> You need to have correct rights to access this cluster. As the administrator of the cluster.

- Connect yourself to [https://kubernetes.iict.ch/](https://kubernetes.iict.ch/)

- Go into the iict cluster

- Click on the `Kubeconfig File` button. 

- Change your [~/.kube/config](~/.kube/config) with the config shown by the dialogue box

> You should backup the old config to be able to switch config with your minikube config

## Deployments

### js-client

> You need to push your image on a image registry like gitlab
> Don't forget to change the image in your deployment config files

- You need to deploy the [java-server_envoy.service.yml](java-server_envoy.service.yml) and [java-server_envoy.deployment.yml](java-server.deployment.yml) first.

- Get the service ip address with:

```sh
kubectl get services
```

- Change the [grpc-client.js](../js-client/webapp/src/grpc-client.js) file with the correct address ip and port.

- Compile the js-client

```sh
cd ../js-client/webapp/
npx webpack src/grpc-client.js
cd ../../kubernetes
```

- Create js-client docker image

```sh
docker build -t <image tag for your registry> ../js-client
```

- Push the new image to your repository

```sh
docker push <image tag for your registry>
```