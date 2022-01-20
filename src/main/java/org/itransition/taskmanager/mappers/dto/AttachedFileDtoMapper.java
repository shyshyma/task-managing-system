package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.models.dtos.AttachedFileDto;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AttachedFileDtoMapper {

    public abstract AttachedFileDto map(AttachedFile attachedFile);
}
