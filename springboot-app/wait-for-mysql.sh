#!/bin/sh
# wait-for-mysql.sh

set -e

host="$1"
shift

until mysql -h "$DB_HOST" -u "$SPRING_DATASOURCE_USERNAME" -p"$SPRING_DATASOURCE_PASSWORD" -e 'select 1'; do
  >&2 echo "MySQL is unavailable - sleeping"
  sleep 1
done

>&2 echo "MySQL is up - executing command"
exec "$@"