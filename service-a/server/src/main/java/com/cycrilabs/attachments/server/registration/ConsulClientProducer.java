package com.cycrilabs.attachments.server.registration;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;

public class ConsulClientProducer {
    @ConfigProperty(name = "consul.host", defaultValue = "localhost")
    String host;
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    int port;
    @Inject
    Vertx vertx;

    @Produces
    public ConsulClient createConsulClient() {
        return ConsulClient.create(vertx, new ConsulClientOptions()
                .setHost(host)
                .setPort(port));
    }
}
