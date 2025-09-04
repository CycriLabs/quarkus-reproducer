package com.cycrilabs.gateway.server.receipts;

import java.util.UUID;

import com.cycrilabs.gateway.server.receipts.entity.AttachmentDTO;
import com.cycrilabs.gateway.server.receipts.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.gateway.server.receipts.entity.ParseReceiptMultipartFormDTO;
import com.cycrilabs.gateway.server.receipts.entity.ReceiptDTO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public interface AttachmentsResource {
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	AttachmentDTO upload(CreateAttachmentMultipartFormDTO payload);

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	Response download(@PathParam("id") final UUID id);

	@POST
	@Path("parse-receipt")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	ReceiptDTO parse(final ParseReceiptMultipartFormDTO input);
}
