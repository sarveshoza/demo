#!/bin/bash

RELEASE_NAME=demo-demo-chart
NAMESPACE=default

# Uninstall the Helm release
helm uninstall $RELEASE_NAME --namespace $NAMESPACE

# Wait for resources to be deleted
echo "Waiting for resources to terminate..."
kubectl wait --for=delete deployment/$RELEASE_NAME -n $NAMESPACE --timeout=60s

echo "Helm release $RELEASE_NAME stopped and resources deleted."