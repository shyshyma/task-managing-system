package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ConsumerConfigJpaMapper {

    public abstract ConsumerConfig map(ConsumerConfigDto consumerConfig);
}
