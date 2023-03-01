#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypass';
	CREATE DATABASE aeon OWNER myuser;
	GRANT ALL PRIVILEGES ON DATABASE aeon TO myuser;
EOSQL