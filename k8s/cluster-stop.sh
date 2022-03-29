#!/bin/sh

cd helm && chmod +x charts-stop.sh && ./charts-stop.sh && cd ..

kubectl delete -f ingresses.yaml

kubectl delete -f services.yaml

kubectl delete -f deployments.yaml

kubectl delete -f secrets.yaml
kubectl delete -f config-maps.yaml

#kubectl delete -f persistent-volume-claims.yaml
#kubectl delete -f persistent-volumes.yaml

kubectl delete -f cluster-role-bindings.yaml
kubectl delete -f cluster-roles.yaml

echo Kubernetes components have been stopped
