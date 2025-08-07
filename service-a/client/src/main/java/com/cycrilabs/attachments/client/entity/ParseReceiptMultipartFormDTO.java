package com.cycrilabs.attachments.client.entity;

import java.io.InputStream;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;

import org.jboss.resteasy.reactive.PartType;

@Getter
@Setter
public class ParseReceiptMultipartFormDTO {
    @FormParam("receipt")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream receipt;
}
