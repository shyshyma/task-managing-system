package org.itransition.taskmanager.mappers;

import org.itransition.taskmanager.dtos.jpa.ConsumerDto;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ConsumerMapper {

    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "secondName", source = "surname")
    @Mapping(target = "taskList", expression = "java(new java.util.ArrayList<>())")
    public abstract Consumer consumerDtoToConsumer(ConsumerDto consumerDto);

    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "secondName")
    public abstract ConsumerDto consumerToConsumerDto(Consumer consumer);
    
    public abstract List<Consumer> consumerDtosToConsumers(List<ConsumerDto> consumerDtos);

    public abstract List<ConsumerDto> consumersToConsumerDtos(List<Consumer> consumerList);
}
