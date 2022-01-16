package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.dtos.jpa.ConsumerDto;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ConsumerDtoMapper {

    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "secondName")
    public abstract ConsumerDto map(Consumer consumer);
}
