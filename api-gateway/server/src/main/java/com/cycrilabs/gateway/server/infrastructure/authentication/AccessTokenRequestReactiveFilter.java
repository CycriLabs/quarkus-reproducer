package com.cycrilabs.gateway.server.infrastructure.authentication;

import jakarta.annotation.Priority;
import jakarta.ws.rs.ext.Provider;

import io.quarkus.oidc.client.reactive.filter.OidcClientRequestReactiveFilter;

@Provider
@Priority(1000)
public class AccessTokenRequestReactiveFilter extends OidcClientRequestReactiveFilter {
    // This class is intentionally empty. It is to ensure that the OIDC client filter is registered
    // for all REST clients. However, keep in mind that this also adds the OIDC token to any calls
    // unrelated to authentication
}
