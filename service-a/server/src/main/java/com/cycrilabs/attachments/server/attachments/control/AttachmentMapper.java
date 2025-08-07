package com.cycrilabs.attachments.server.attachments.control;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.cycrilabs.attachments.client.entity.AttachmentDTO;
import com.cycrilabs.attachments.client.entity.CreateAttachmentDTO;
import com.cycrilabs.attachments.server.attachments.entity.Attachment;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface AttachmentMapper {
    AttachmentDTO toDTO(final Attachment entity);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
    })
    Attachment fromDTO(final CreateAttachmentDTO dto);
}
