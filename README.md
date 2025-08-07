# Quarkus Reproducer

This repository is used to reproduce issues with Quarkus.

## Overview

The projects consist of two Quarkus applications:

- `service-a`: A simple REST service that provides two endpoints
- `api-gateway`: An API gateway that routes requests to `service-a`

The infrastructure uses Keycloak for authentication and authorization,
`service-a` uses a PostgreSQL database.

Routing is done by using Consul.

## Usage

1. Clone the repository:
   ```bash
   git clone git@github.com:CycriLabs/quarkus-reproducer.git
   cd quarkus-reproducer
   ```
2. Build the project:
   ```bash
   mvnd clean package
   ```
3. Run the setup script:
   ```bash
   ./setup.sh
   ```
4. Start both applications, `service-a` and `api-gateway`.

Then, the `test.http` file can be used to test the endpoints.
