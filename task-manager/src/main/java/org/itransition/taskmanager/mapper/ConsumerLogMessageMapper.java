package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.mb.ConsumerLogMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ConsumerLogMessageMapper {

    public abstract ConsumerLogMessage map(String firstName, String lastName, String message);
}
