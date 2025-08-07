package com.cycrilabs.attachments.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://service-a-server")
public interface AttachmentsClient extends AttachmentsResource {
}
