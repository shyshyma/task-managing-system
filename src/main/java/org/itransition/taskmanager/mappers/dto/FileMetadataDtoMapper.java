package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.models.dto.FileMetadataDto;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class FileMetadataDtoMapper {

   @Mapping(target = "fileName", source = "attachedFile.name")
   @Mapping(target = "downloadPath", source = "downloadPath")
   @Mapping(target = "size", expression = "java(attachedFile.getData().length)")
   public abstract FileMetadataDto map(AttachedFile attachedFile, String downloadPath);
}
