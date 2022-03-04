package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.mb.TaskLogMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TaskLogMessageMapper {

    public abstract TaskLogMessage map(String taskTitle, String message);
}
