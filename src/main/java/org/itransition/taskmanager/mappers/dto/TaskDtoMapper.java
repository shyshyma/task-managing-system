package org.itransition.taskmanager.mappers.dto;

import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.models.jpa.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TaskDtoMapper {

    public abstract TaskDto map(Task task);
}