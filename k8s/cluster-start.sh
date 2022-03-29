#!/bin/sh

kubectl apply -f secrets.yaml
kubectl apply -f config-maps.yaml

cd helm && chmod +x charts-init.sh && ./charts-init.sh && cd ..

kubectl apply -f cluster-roles.yaml
kubectl apply -f cluster-role-bindings.yaml

kubectl apply -f deployments.yaml

kubectl apply -f services.yaml

kubectl apply -f ingresses.yaml

echo Kubernetes components have been started
