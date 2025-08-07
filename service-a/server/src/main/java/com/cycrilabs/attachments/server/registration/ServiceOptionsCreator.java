package com.cycrilabs.attachments.server.registration;

import java.util.Map;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.Config;

import io.quarkus.logging.Log;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ServiceOptions;

@ApplicationScoped
public class ServiceOptionsCreator {
    public static final String ENV_APPLICATION_NAME = "quarkus.application.name";
    public static final String ENV_APPLICATION_VERSION = "quarkus.application.version";
    public static final String ENV_HTTP_PORT = "quarkus.http.port";
    public static final String ENV_HOSTNAME = "hostname";
    public static final String ENV_HOSTNAME_SERVICE = "hostname.service";
    public static final String ENV_HOSTNAME_HEALTH = "hostname.health";

    public static final String DEFAULT_SERVICE_ADDRESS = "127.0.0.1";
    public static final String DEFAULT_HEALTH_SERVICE_ADDRESS = "host.docker.internal";

    private final Config config;

    public ServiceOptionsCreator(final Config config) {
        this.config = config;
    }

    public ServiceOptions createServiceOptions(final int instanceId) {
        final String applicationName = config.getValue(ENV_APPLICATION_NAME, String.class);
        final String applicationVersion = config.getValue(ENV_APPLICATION_VERSION, String.class);
        // port must be loaded via config as it may not be available via injection yet
        final String port = config.getValue(ENV_HTTP_PORT, String.class);
        final Optional<String> hostname = config.getOptionalValue(ENV_HOSTNAME, String.class);
        final Optional<String> hostnameService =
                config.getOptionalValue(ENV_HOSTNAME_SERVICE, String.class);
        final Optional<String> hostnameHealth =
                config.getOptionalValue(ENV_HOSTNAME_HEALTH, String.class);

        final String address = hostnameService.or(() -> hostname).orElse(DEFAULT_SERVICE_ADDRESS);
        // TODO(Marc)
        //  currently this must be specified like this as consul runs inside docker,
        //  the service itself may run in docker or outside on the host machine.
        //  However, on linux this is currently not working out of the box
        final String healthAddress =
                hostnameHealth.or(() -> hostname).orElse(DEFAULT_HEALTH_SERVICE_ADDRESS);

        Log.infov("Trying to register service with address {0} and port {1}", address, port);

        final String serviceId = applicationName + "-" + instanceId;
        return new ServiceOptions()
                .setId(serviceId)
                .setName(applicationName)
                .setAddress(address)
                .setPort(Integer.parseInt(port))
                .setMeta(Map.of("version", applicationVersion))
                .setCheckOptions(createCheckOptions(serviceId, healthAddress, port));
    }

    private CheckOptions createCheckOptions(final String serviceId, final String address,
            final String port) {
        return new CheckOptions()
                .setId(serviceId + "_health_check")
                .setServiceId(serviceId)
                .setInterval("15s")
                .setHttp(createHealthUrl(address, port))
                .setNotes("Calls microprofile health service")
                .setDeregisterAfter("30s");
    }

    private String createHealthUrl(final String address, final String port) {
        final String healthUrl = "http://" + address + ":" + port + "/q/health";
        Log.infov("Health check url: {0}", healthUrl);
        return healthUrl;
    }
}
