package org.itransition.taskmanager.mappers;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.ConsumerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    @Setter(onMethod_ = @Autowired)
    protected ConsumerRepository consumerRepository;

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "attachedFilesList", source = "attachedFileToTaskDtoList")
    @Mapping(target = "consumer",
            expression = "java(consumerRepository.findById(taskDto.getConsumerId())" +
                    ".orElseThrow(()-> new RuntimeException(\"No consumer was found by current id\")))")
    public abstract Task taskDtoToTask(TaskDto taskDto);

    @Mapping(target = "consumerId", source = "consumer.id")
    @Mapping(target = "attachedFileToTaskDtoList", source = "attachedFilesList")
    public abstract TaskDto taskToTaskDto(Task task);

    public abstract List<Task> taskDtosToTasks(List<TaskDto> taskDtos);

    public abstract List<TaskDto> tasksToTaskDtos(List<Task> taskList);
}
