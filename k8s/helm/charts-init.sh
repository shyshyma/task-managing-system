#!/bin/sh

helm install mysql-helm-service bitnami/mysql -f mysql.yaml
#version needs caused by bug with existingSecret, see https://github.com/bitnami/charts/issues/8693
helm install mongodb-helm-service bitnami/mongodb --version=10.28.7 -f mongodb.yaml
helm install redis-helm-service bitnami/redis -f redis.yaml
helm install rabbitmq-helm-service bitnami/rabbitmq -f rabbitmq.yaml

echo Charts have been started
