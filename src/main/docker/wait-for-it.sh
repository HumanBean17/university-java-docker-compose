#!/bin/sh
# wait-for-postgres.sh

until curl "http://0.0.0.0:9200"; do
  echo "Elastic is unavailable - sleeping"
  sleep 1
done

echo "Elastic is up - executing command"
exec "$@"