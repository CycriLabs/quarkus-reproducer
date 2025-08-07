CREATE USER quarkus WITH PASSWORD 'quarkus-pw';

CREATE DATABASE keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO quarkus;
\c keycloak
CREATE SCHEMA keycloak AUTHORIZATION quarkus;
GRANT ALL ON SCHEMA keycloak TO quarkus;
ALTER USER quarkus SET SEARCH_PATH = 'keycloak';

-- Service related databases
CREATE DATABASE service_a;
GRANT ALL PRIVILEGES ON DATABASE service_a TO quarkus;
\c service_a
CREATE SCHEMA service_a AUTHORIZATION quarkus;
GRANT ALL ON SCHEMA service_a TO quarkus;
ALTER USER quarkus SET SEARCH_PATH = 'service_a';
