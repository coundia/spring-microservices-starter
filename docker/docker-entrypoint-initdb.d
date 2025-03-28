#!/bin/bash
set -e

# Create the test database using the same user
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE pcoundia_test WITH OWNER = $POSTGRES_USER;
EOSQL
