package com.cycrilabs.gateway.server.receipts;

import java.io.InputStream;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.cycrilabs.gateway.server.receipts.entity.AttachmentDTO;
import com.cycrilabs.gateway.server.receipts.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.gateway.server.receipts.entity.ParseReceiptMultipartFormDTO;
import com.cycrilabs.gateway.server.receipts.entity.ReceiptDTO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("receipts")
public class ReceiptResource {
	private final AttachmentsClient attachmentsClient;

	public ReceiptResource(
			@RestClient final AttachmentsClient attachmentsClient
	) {
		this.attachmentsClient = attachmentsClient;
	}

	@POST
	@Path("receipt-transaction")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ReceiptDTO createReceiptTransaction(final CreateAttachmentMultipartFormDTO request) {
		final AttachmentDTO attachment = attachmentsClient.upload(request);
		final Response download = attachmentsClient.download(attachment.getId());
		final ParseReceiptMultipartFormDTO payload = new ParseReceiptMultipartFormDTO();
		payload.setReceipt(download.readEntity(InputStream.class));
		return attachmentsClient.parse(payload);
	}
}
