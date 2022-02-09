package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ConsumerJpaMapper {

    public abstract Consumer map(ConsumerDto consumerDto);

    @Mapping(target = "id", ignore = true)
    public abstract void mapWithoutId(ConsumerDto consumerDto, @MappingTarget Consumer consumer);
}
