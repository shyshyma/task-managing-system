package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public abstract class AttachedFileJpaMapper {

    @Mapping(target = "data", source = "content", qualifiedByName = "decodeBase64")
    public abstract AttachedFile map(AttachedFileDto attachedFileDto);

    @Named("decodeBase64")
    public byte[] decodeBase64(String content) {
        return Base64.getDecoder().decode(content);
    }
}
