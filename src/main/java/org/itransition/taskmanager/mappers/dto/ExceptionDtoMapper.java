package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.models.dtos.ExceptionMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;

@Mapper(componentModel = "spring", imports = {Date.class, Instant.class})
public abstract class ExceptionDtoMapper {

    @Mapping(target = "message", source = "ex.message")
    @Mapping(target = "statusCode", expression = "java(status.value())")
    @Mapping(target = "timestamp", expression = "java(Date.from(Instant.now()))",
            dateFormat = "dd/MM/yyyy HH:mm:ss")
    public abstract ExceptionMetadataDto map(Exception ex, HttpStatus status);
}
