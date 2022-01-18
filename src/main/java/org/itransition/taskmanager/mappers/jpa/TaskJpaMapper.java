package org.itransition.taskmanager.mappers.jpa;

import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.models.jpa.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TaskJpaMapper {

    public abstract Task map(TaskDto taskDto);

    @Mapping(target = "id", source = "id")
    public abstract Task mapWithId(TaskDto taskDto, Long id);
}
