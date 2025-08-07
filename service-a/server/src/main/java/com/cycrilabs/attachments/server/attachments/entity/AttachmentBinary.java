package com.cycrilabs.attachments.server.attachments.entity;

import java.sql.Blob;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AttachmentBinary {
    @Id
    private UUID id;
    @Lob
    private Blob binaryData;
    @OneToOne
    @MapsId
    private Attachment attachment;
}
