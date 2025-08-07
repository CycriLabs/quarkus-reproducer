#!/bin/bash

# CLI flag variables
STAGE=dev
KEYCLOAK_CONFIGURE=true
KEYCLOAK_EXPORT=true

echo "Starting server with stage: $STAGE"

# Load environment variables
source .env

# Define additional variables
SERVER_DIR_NAME=$(basename "$PWD")
CONFIG_DIR_NAME="setup"
SERVICES_ENV_DIR_NAME="services-env"
SECRET_TEMPLATES_DIR_NAME="secret-templates"
KEYCLOAK_CONFIGURATION_DIR_NAME="keycloak-config"

SERVER_DIR=$(pwd)
CONFIG_DIR="$SERVER_DIR/$CONFIG_DIR_NAME"
SERVICES_ENV_DIR="$CONFIG_DIR/$SERVICES_ENV_DIR_NAME"
SECRET_TEMPLATES_DIR="$CONFIG_DIR/$SECRET_TEMPLATES_DIR_NAME"
KEYCLOAK_CONFIGURATION_DIR="$CONFIG_DIR/$KEYCLOAK_CONFIGURATION_DIR_NAME"

# Start dev services
echo "Starting backing services..."
docker compose up -d --wait

# Configure keycloak at first, it is required for exporting secrets later
if $KEYCLOAK_CONFIGURE ; then
  echo "Configuring keycloak..."

  # Configure keycloak
  docker run \
      --pull=always \
      --add-host=keycloak:host-gateway \
      --mount type=bind,src="$KEYCLOAK_CONFIGURATION_DIR",target="/configuration,readonly" \
      --rm ghcr.io/cycrilabs/keycloak-configurator:$VERSION_KC_CONFIGURATOR configure -s $KC_HOSTNAME -u $KC_USER -p $KC_PASSWORD -c //configuration
fi

# Export secrets & prepare backing services .env files
if $KEYCLOAK_EXPORT ; then
  echo "Exporting keycloak client secrets to env files..."

  docker run \
      --pull=always \
      --add-host=keycloak:host-gateway \
      --mount type=bind,src="$SECRET_TEMPLATES_DIR",target="/configuration,readonly" \
      --mount type=bind,src="$SERVICES_ENV_DIR",target="/output" \
      --env-file .env \
      --rm ghcr.io/cycrilabs/keycloak-configurator:$VERSION_KC_CONFIGURATOR export-secrets -s $KC_HOSTNAME -u $KC_USER -p $KC_PASSWORD -r default -c //configuration -o //output

  echo "Copying secrets to backing services..."
  for SERVICE_ENV in "$SERVICES_ENV_DIR"/*
  do
    FILENAME=$(basename -- "$SERVICE_ENV")
    SERVICE="${FILENAME%.*}"
    echo "Copying '$SERVICE' to '$SERVICE/server/.env'"
    cp $SERVICES_ENV_DIR/$SERVICE.env $SERVICE/server/.env
  done
fi
