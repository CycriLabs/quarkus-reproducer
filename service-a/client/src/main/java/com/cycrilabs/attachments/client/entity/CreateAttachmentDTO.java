package com.cycrilabs.attachments.client.entity;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAttachmentDTO {
    @NotNull
    private String name;
}
