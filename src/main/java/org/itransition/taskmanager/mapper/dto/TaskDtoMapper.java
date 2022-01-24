package org.itransition.taskmanager.mapper.dto;

import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TaskDtoMapper {

    public abstract TaskDto map(Task task);
}
