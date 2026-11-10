#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd -- "$SCRIPT_DIR/.." && pwd)"
CHART_PATH="$PROJECT_ROOT/demo-chart"
RELEASE_NAME=demo-demo-chart
NAMESPACE=default
DB_URL="${DB_URL:-}"
DB_USERNAME="${DB_USERNAME:-neondb_owner}"
DB_PASSWORD="${DB_PASSWORD:-}"
: "${DB_URL:?Set DB_URL to the Neon JDBC connection URL}"
: "${DB_PASSWORD:?Set DB_PASSWORD in your environment; do not put it in chart values}"

echo "Using PostgreSQL database configured by DB_URL"
"$PROJECT_ROOT/mvnw" -f "$PROJECT_ROOT/pom.xml" -DskipTests clean package

JAR_PATH="$(find "$PROJECT_ROOT/target" -maxdepth 1 -type f \
  -name 'demo-*.jar' ! -name '*.original' -print -quit)"
if [[ -z "$JAR_PATH" ]]; then
  echo "No packaged demo JAR found under $PROJECT_ROOT/target" >&2
  exit 1
fi

IMAGE_TAG="${JAR_PATH##*/demo-}"
IMAGE_TAG="${IMAGE_TAG%.jar}"
IMAGE="sarvesh10/spring-hello:$IMAGE_TAG"

docker build -t "$IMAGE" "$PROJECT_ROOT"
docker push "$IMAGE"

# Create/update the Kubernetes Secret without putting the password in Helm values.
kubectl create secret generic neon-db-credentials \
  --namespace "$NAMESPACE" \
  --from-file=password=<(printf '%s' "$DB_PASSWORD") \
  --dry-run=client -o yaml | kubectl apply -f -

# Install or upgrade the Helm release.
helm upgrade --install "$RELEASE_NAME" "$CHART_PATH" \
  --namespace "$NAMESPACE" --create-namespace \
  --set image.tag="$IMAGE_TAG" \
  --set mssql.enabled=false \
  --set-string database.url="$DB_URL" \
  --set-string database.username="$DB_USERNAME" \
  --set-string database.passwordSecretName=neon-db-credentials

echo "Waiting for service..."
kubectl rollout status deployment/"$RELEASE_NAME" -n "$NAMESPACE" --timeout=360s
kubectl get svc "$RELEASE_NAME" -n "$NAMESPACE"

echo "Starting the Minikube service tunnel. Keep this terminal open while using the URL."
minikube service "$RELEASE_NAME" -n "$NAMESPACE" --url
