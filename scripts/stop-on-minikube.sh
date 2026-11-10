#!/bin/bash
# Script to stop and clean up Spring Boot app from Minikube

# Delete deployment and service
kubectl delete -f spring-hello-deployments.yaml
kubectl delete -f spring-hello-service.yaml

minikube stop
