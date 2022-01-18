package org.itransition.taskmanager.mappers.jpa;

import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public abstract class AttachedFileJpaMapper {

    @Mapping(target = "data", expression = "java(multipartFile.getBytes())")
    @Mapping(target = "name", expression = "java(multipartFile.getOriginalFilename())")
    public abstract AttachedFile map(MultipartFile multipartFile) throws IOException;
}
