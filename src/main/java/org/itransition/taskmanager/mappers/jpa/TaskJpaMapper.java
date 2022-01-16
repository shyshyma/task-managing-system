package org.itransition.taskmanager.mappers.jpa;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.ConsumerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TaskJpaMapper {

    private static final String MODEL_NOT_FOUND_EXCEPTION_PATH =
            "org.itransition.taskmanager.exceptions.ModelNotFoundException";

    @Setter(onMethod_ = @Autowired)
    protected ConsumerRepository consumerRepository;

    @Mapping(target = "id", source = "taskId")
    @Mapping(target = "consumer", expression = "java(consumerRepository.findById(consumerId)" +
            ".orElseThrow(()-> new " + MODEL_NOT_FOUND_EXCEPTION_PATH + "(consumerId)))")
    public abstract Task map(TaskDto taskDto, Long taskId, Long consumerId);

    @Mapping(target = "consumer", expression = "java(consumerRepository.findById(consumerId)" +
            ".orElseThrow(()-> new " + MODEL_NOT_FOUND_EXCEPTION_PATH + "(consumerId)))")
    public abstract Task map(TaskDto taskDto, Long consumerId);
}
