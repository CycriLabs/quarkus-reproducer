package com.cycrilabs.attachments.server;

import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import lombok.RequiredArgsConstructor;

import com.cycrilabs.attachments.server.attachments.control.AttachmentsController;
import com.cycrilabs.attachments.server.attachments.entity.Attachment;

@RequiredArgsConstructor
@RequestScoped
@Path("")
public class AttachmentsResource {
    private final AttachmentsController controller;

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public AttachmentDTO upload(final CreateAttachmentMultipartFormDTO payload) {
        return controller.saveAttachment(payload.getAttachment(), payload.getPayload());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(final UUID id) {
        final Attachment attachment = controller.loadAttachment(id);
        final StreamingOutput streamingOutput =
                outputStream -> controller.getAttachmentStream(attachment, outputStream);

        return Response.ok(streamingOutput)
                .header("Content-Disposition",
                        "inline; filename=\"" + attachment.getName() + "\"")
                .build();
    }

    @POST
    @Path("parse-receipt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ReceiptDTO parse(final ParseReceiptMultipartFormDTO input) {
        return new ReceiptDTO();
    }
}
