package org.itransition.audit.mapper;

import org.mapstruct.Mapping;
import org.itransition.audit.mongo.entity.AttachedFileLog;
import org.itransition.audit.mq.AttachedFileLogMessage;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public abstract class AttachedFileLogMapper {

    @Mapping(target = "timeStamp", expression = "java(LocalDate.now())")
    public abstract AttachedFileLog map(AttachedFileLogMessage attachedFileLogMessage);
}
