package org.itransition.taskmanager.mapper.jpa;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ConsumerJpaMapper {

    public abstract Consumer map(ConsumerDto consumerDto);
}
