#!/bin/sh

helm delete mysql-helm-service
helm delete mongodb-helm-service
helm delete rabbitmq-helm-service
helm delete redis-helm-service

echo Charts have been stopped
