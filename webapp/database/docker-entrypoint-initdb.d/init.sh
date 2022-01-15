#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER ticket WITH PASSWORD 'ticket' CREATEDB;
	
    CREATE DATABASE ticketdb OWNER ticket;
	GRANT ALL PRIVILEGES ON DATABASE ticketdb TO ticket;
EOSQL