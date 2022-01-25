package org.itransition.taskmanager.mapper.dto;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ConsumerDtoMapper {

    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "secondName")
    public abstract ConsumerDto map(Consumer consumer);
}
