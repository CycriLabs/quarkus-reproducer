package com.cycrilabs.attachments.client;

import java.util.UUID;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.cycrilabs.attachments.client.entity.AttachmentDTO;
import com.cycrilabs.attachments.client.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ParseReceiptMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ReceiptDTO;

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
