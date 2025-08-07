package com.cycrilabs.attachments.server.attachments.control;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;

import lombok.RequiredArgsConstructor;

import org.hibernate.engine.jdbc.proxy.BlobProxy;

import com.cycrilabs.attachments.client.entity.AttachmentDTO;
import com.cycrilabs.attachments.client.entity.CreateAttachmentDTO;
import com.cycrilabs.attachments.server.attachments.boundary.AttachmentsRepository;
import com.cycrilabs.attachments.server.attachments.entity.Attachment;
import com.cycrilabs.attachments.server.attachments.entity.AttachmentBinary;

@RequiredArgsConstructor
@Transactional
@ApplicationScoped
public class AttachmentsController {
    private final AttachmentMapper mapper;
    private final AttachmentsRepository repository;

    public AttachmentDTO saveAttachment(final InputStream inputStream,
            final CreateAttachmentDTO payload) {
        final Attachment attachment = mapper.fromDTO(payload);
        repository.persist(attachment);

        final AttachmentBinary attachmentBinary = new AttachmentBinary();
        attachmentBinary.setAttachment(attachment);
        attachmentBinary.setBinaryData(BlobProxy.generateProxy(inputStream, Long.MAX_VALUE));
        repository.persist(attachmentBinary);

        return mapper.toDTO(attachment);
    }

    public Attachment loadAttachment(final UUID id) {
        final Attachment attachment = repository.findById(id);
        if (attachment == null) {
            throw new NotFoundException("Attachment not found");
        }
        return attachment;
    }

    public void getAttachmentStream(final Attachment attachment, final OutputStream outputStream) {
        try (final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                outputStream);
                final InputStream contentStream = repository.findByAttachment(attachment)
                        .getBinaryData()
                        .getBinaryStream()) {
            contentStream.transferTo(bufferedOutputStream);
        } catch (final Exception e) {
            throw new InternalServerErrorException(e);
        }
    }
}
