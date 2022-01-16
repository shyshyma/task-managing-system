package org.itransition.taskmanager.mappers.jpa;

import org.itransition.taskmanager.dtos.jpa.ConsumerDto;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ConsumerJpaMapper {

    @Mapping(target = "firstName", source = "consumerDto.name")
    @Mapping(target = "secondName", source = "consumerDto.surname")
    @Mapping(target = "id", source = "consumerId")
    public abstract Consumer map(ConsumerDto consumerDto, Long consumerId);

    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "secondName", source = "surname")
    public abstract Consumer map(ConsumerDto consumerDto);
}
