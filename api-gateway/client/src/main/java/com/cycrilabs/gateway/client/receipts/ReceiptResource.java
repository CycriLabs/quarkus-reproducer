package com.cycrilabs.gateway.client.receipts;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.cycrilabs.attachments.client.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ReceiptDTO;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ReceiptResource {
    @POST
    @Path("receipt-transaction")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    ReceiptDTO createReceiptTransaction(final CreateAttachmentMultipartFormDTO request);
}
