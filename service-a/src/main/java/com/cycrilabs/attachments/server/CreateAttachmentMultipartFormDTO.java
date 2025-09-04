package com.cycrilabs.attachments.server;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

@Getter
@Setter
public class CreateAttachmentMultipartFormDTO {
    @FormParam("attachment")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream attachment;
    @RestForm
    @PartType(MediaType.APPLICATION_JSON)
    private CreateAttachmentDTO payload;
}
