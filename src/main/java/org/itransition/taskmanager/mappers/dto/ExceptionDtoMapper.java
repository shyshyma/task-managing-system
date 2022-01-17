package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.dtos.ExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public abstract class ExceptionDtoMapper {

    @Mapping(target = "timestamp", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "message", expression = "java(ex.getMessage())")
    @Mapping(target = "statusCode", expression = "java(status.value())")
    public abstract ExceptionDto map(Exception ex, HttpStatus status);
}
