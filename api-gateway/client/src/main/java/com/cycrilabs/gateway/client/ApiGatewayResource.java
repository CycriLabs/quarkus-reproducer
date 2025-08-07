package com.cycrilabs.gateway.client;

import jakarta.ws.rs.Path;

import com.cycrilabs.gateway.client.receipts.ReceiptResource;

@Path("/")
public interface ApiGatewayResource {
    @Path("receipts")
    ReceiptResource receipts();
}
