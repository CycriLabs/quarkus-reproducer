package com.cycrilabs.gateway.server;

import jakarta.enterprise.context.RequestScoped;

import lombok.RequiredArgsConstructor;

import com.cycrilabs.gateway.client.ApiGatewayResource;
import com.cycrilabs.gateway.client.receipts.ReceiptResource;

@RequiredArgsConstructor
@RequestScoped
public class ApiGatewayResourceImpl implements ApiGatewayResource {
    private final ReceiptResource receiptResource;

    @Override
    public ReceiptResource receipts() {
        return receiptResource;
    }
}
