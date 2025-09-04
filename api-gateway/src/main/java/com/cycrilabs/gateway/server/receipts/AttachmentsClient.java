package com.cycrilabs.gateway.server.receipts;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://service-a")
public interface AttachmentsClient extends AttachmentsResource {
}
