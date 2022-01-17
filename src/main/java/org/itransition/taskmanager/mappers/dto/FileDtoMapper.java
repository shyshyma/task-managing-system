package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.dtos.FileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public abstract class FileDtoMapper {

    @Mapping(target = "fileName", expression = "java(multipartFile.getOriginalFilename())")
    @Mapping(target = "size", expression = "java(multipartFile.getBytes().length)")
    @Mapping(target = "downloadPath", source = "pathForDownloadingFile")
    public abstract FileDto map(MultipartFile multipartFile, String pathForDownloadingFile) throws IOException;
}
