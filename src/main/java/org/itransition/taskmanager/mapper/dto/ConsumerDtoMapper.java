package org.itransition.taskmanager.mapper.dto;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ConsumerDtoMapper {

    public abstract ConsumerDto map(Consumer consumer, String email);
}
