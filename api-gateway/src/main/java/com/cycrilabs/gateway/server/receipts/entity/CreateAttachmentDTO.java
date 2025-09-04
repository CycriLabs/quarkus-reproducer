package com.cycrilabs.gateway.server.receipts.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAttachmentDTO {
    @NotNull
    private String name;
}
