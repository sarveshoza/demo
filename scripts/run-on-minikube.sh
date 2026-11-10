#!/bin/bash
# Script to deploy and open Spring Boot app on Minikube

docker build -t sarvesh10/spring-hello:latest .
docker push sarvesh10/spring-hello:latest

# Start Minikube if not running
minikube status | grep -q "Running" || minikube start

# Set Minikube dashboard URL
#minikube dashboard --url=true

# Apply deployment and service YAMLs
kubectl apply -f spring-hello-deployments.yaml
kubectl apply -f spring-hello-service.yaml

# Wait for deployment rollout
kubectl rollout status deployment/spring-hello

# Open the service in browser
minikube service spring-hello-svc
