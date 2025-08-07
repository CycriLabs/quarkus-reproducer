package com.cycrilabs.attachments.server.attachments.boundary;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import com.cycrilabs.attachments.server.attachments.entity.Attachment;
import com.cycrilabs.attachments.server.attachments.entity.AttachmentBinary;

@RequiredArgsConstructor
@ApplicationScoped
public class AttachmentsRepository {
    private final EntityManager entityManager;

    public void persist(final Attachment attachment) {
        entityManager.persist(attachment);
    }

    public void persist(final AttachmentBinary attachmentBinary) {
        entityManager.persist(attachmentBinary);
    }

    public Attachment findById(final UUID id) {
        return entityManager.find(Attachment.class, id);
    }

    public AttachmentBinary findByAttachment(final Attachment attachment) {
        return entityManager.find(AttachmentBinary.class, attachment.getId());
    }
}
