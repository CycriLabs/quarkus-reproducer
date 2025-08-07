package com.cycrilabs.gateway.server;

import jakarta.annotation.Priority;
import jakarta.interceptor.Interceptor;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestContext;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestFilter;

@Provider
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 100)
public class CustomLoggingFilter implements ResteasyReactiveClientRequestFilter {
    private static final Logger LOG = Logger.getLogger(CustomLoggingFilter.class);

    @Override
    public void filter(final ResteasyReactiveClientRequestContext requestContext) {
        LOG.infof("Resolved address by Stork: %s", requestContext.getUri().toString());
    }
}
