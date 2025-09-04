package com.cycrilabs.attachments.server;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAttachmentDTO {
    @NotNull
    private String name;
}
