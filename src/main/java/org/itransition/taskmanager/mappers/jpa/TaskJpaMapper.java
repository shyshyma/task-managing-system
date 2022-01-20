package org.itransition.taskmanager.mappers.jpa;

import org.itransition.taskmanager.models.dtos.TaskDto;
import org.itransition.taskmanager.models.jpa.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TaskJpaMapper {

    public abstract Task map(TaskDto taskDto);
}
