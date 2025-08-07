package com.cycrilabs.gateway.server.receipts;

import java.io.InputStream;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.cycrilabs.attachments.client.AttachmentsClient;
import com.cycrilabs.attachments.client.entity.AttachmentDTO;
import com.cycrilabs.attachments.client.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ParseReceiptMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ReceiptDTO;
import com.cycrilabs.gateway.client.receipts.ReceiptResource;

@RequestScoped
public class ReceiptResourceImpl implements ReceiptResource {
    private final AttachmentsClient attachmentsClient;

    public ReceiptResourceImpl(
            @RestClient final AttachmentsClient attachmentsClient
    ) {
        this.attachmentsClient = attachmentsClient;
    }

    @Override
    public ReceiptDTO createReceiptTransaction(final CreateAttachmentMultipartFormDTO request) {
        final AttachmentDTO attachment = attachmentsClient.upload(request);
        final Response download = attachmentsClient.download(attachment.getId());
        final ParseReceiptMultipartFormDTO payload = new ParseReceiptMultipartFormDTO();
        payload.setReceipt(download.readEntity(InputStream.class));
        return attachmentsClient.parse(payload);
    }
}
