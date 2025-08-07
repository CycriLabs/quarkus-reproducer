package com.cycrilabs.attachments.server;

import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import lombok.RequiredArgsConstructor;

import com.cycrilabs.attachments.client.AttachmentsResource;
import com.cycrilabs.attachments.client.entity.AttachmentDTO;
import com.cycrilabs.attachments.client.entity.CreateAttachmentMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ParseReceiptMultipartFormDTO;
import com.cycrilabs.attachments.client.entity.ReceiptDTO;
import com.cycrilabs.attachments.server.attachments.control.AttachmentsController;
import com.cycrilabs.attachments.server.attachments.entity.Attachment;

@RequiredArgsConstructor
@RequestScoped
public class AttachmentsResourceImpl implements AttachmentsResource {
    private final AttachmentsController controller;

    @Override
    public AttachmentDTO upload(final CreateAttachmentMultipartFormDTO payload) {
        return controller.saveAttachment(payload.getAttachment(), payload.getPayload());
    }

    @Override
    public Response download(final UUID id) {
        final Attachment attachment = controller.loadAttachment(id);
        final StreamingOutput streamingOutput =
                outputStream -> controller.getAttachmentStream(attachment, outputStream);

        return Response.ok(streamingOutput)
                .header("Content-Disposition",
                        "inline; filename=\"" + attachment.getName() + "\"")
                .build();
    }

    @Override
    public ReceiptDTO parse(final ParseReceiptMultipartFormDTO input) {
        return new ReceiptDTO();
    }
}
