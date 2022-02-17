package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.dto.FileMetadataDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class FileMetadataDtoMapper {

    @Mapping(target = "fileName", source = "attachedFile.name")
    @Mapping(target = "downloadPath", source = "downloadPath")
    @Mapping(target = "size", expression = "java(attachedFile.getData().length)")
    public abstract FileMetadataDto map(AttachedFile attachedFile, String downloadPath);
}
