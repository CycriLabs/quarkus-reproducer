package com.cycrilabs.attachments.server;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

import org.jboss.resteasy.reactive.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

@Getter
@Setter
public class ParseReceiptMultipartFormDTO {
    @FormParam("receipt")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream receipt;
}
