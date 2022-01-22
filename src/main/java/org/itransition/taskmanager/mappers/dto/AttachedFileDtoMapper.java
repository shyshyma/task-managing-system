package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.models.dto.AttachedFileDto;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public abstract class AttachedFileDtoMapper {

    @Mapping(target = "content", source = "data", qualifiedByName = "encodeBase64")
    public abstract AttachedFileDto map(AttachedFile attachedFile);

    @Named("encodeBase64")
    public String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
