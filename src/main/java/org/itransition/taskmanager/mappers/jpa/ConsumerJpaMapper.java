package org.itransition.taskmanager.mappers.jpa;

import org.itransition.taskmanager.models.dto.ConsumerDto;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ConsumerJpaMapper {

    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "secondName", source = "surname")
    public abstract Consumer map(ConsumerDto consumerDto);
}
