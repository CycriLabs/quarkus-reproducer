package com.cycrilabs.attachments.server.registration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.ServiceList;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.ext.consul.ConsulClient;

@ApplicationScoped
public class ServiceRegistration {
    @ConfigProperty(name = "quarkus.application.name")
    String name;
    @Inject
    ConsulClient consulClient;
    @Inject
    ServiceOptionsCreator serviceOptionsCreator;

    private ScheduledExecutorService executorService;
    private String serviceId;

    public void onInit(@Observes final StartupEvent ev) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try {
                registerService();
                executorService.shutdown();
            } catch (final Throwable t) {
                Log.errorv("Caught exception in ScheduledExecutorService: {0}", t.getMessage());
            }
        }, 500, TimeUnit.MILLISECONDS);
    }

    public void onStop(@Observes final ShutdownEvent ev) {
        consulClient.deregisterServiceAndAwait(serviceId);
        Log.infov("Instance deregistered: id={0}", serviceId);
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private void registerService() {
        final ServiceList services = consulClient.catalogServiceNodesAndAwait(name);
        final int instanceId = services.getList().size();
        final ServiceOptions service = serviceOptionsCreator.createServiceOptions(instanceId);
        serviceId = service.getId();
        consulClient.registerServiceAndAwait(service);
        Log.infov("Instance registered: id={0}", service.getId());
    }
}
