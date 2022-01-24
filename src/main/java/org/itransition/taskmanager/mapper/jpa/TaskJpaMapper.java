package org.itransition.taskmanager.mapper.jpa;

import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TaskJpaMapper {

    public abstract Task map(TaskDto taskDto);
}
