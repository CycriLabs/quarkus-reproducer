package com.cycrilabs.attachments.server.attachments.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
public class Attachment {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
}
