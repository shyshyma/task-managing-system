package org.itransition.audit.mapper;

import org.itransition.audit.mongo.entity.TaskLog;
import org.itransition.audit.mq.TaskLogMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public abstract class TaskLogMapper {

    @Mapping(target = "timeStamp", expression = "java(LocalDate.now())")
    public abstract TaskLog map(TaskLogMessage taskLogMessage);
}
