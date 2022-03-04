package org.itransition.audit.mapper;

import org.mapstruct.Mapper;
import org.itransition.audit.mongo.entity.ConsumerLog;
import org.itransition.audit.mq.ConsumerLogMessage;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public abstract class ConsumerLogMapper {

    @Mapping(target = "timeStamp", expression = "java(LocalDate.now())")
    public abstract ConsumerLog map(ConsumerLogMessage consumerLogMessage);
}
