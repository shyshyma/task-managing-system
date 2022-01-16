package org.itransition.taskmanager.mappers.jpa;

import lombok.Setter;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.repositories.jpa.TaskRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public abstract class AttachedFileJpaMapper {

    private static final String JPA_MODEL_NOT_FOUND_EXCEPTION_PATH =
            "org.itransition.taskmanager.exceptions.JpaModelNotFoundException";

    @Setter(onMethod_ = @Autowired)
    protected TaskRepository taskRepository;

    @Mapping(target = "data", expression = "java(multipartFile.getBytes())")
    @Mapping(target = "name", expression = "java(multipartFile.getName())")
    @Mapping(target = "task", expression = "java(taskRepository.findById(taskId)" +
            ".orElseThrow(()-> new " + JPA_MODEL_NOT_FOUND_EXCEPTION_PATH + "(taskId)))")
    public abstract AttachedFile map(MultipartFile multipartFile, Long taskId) throws IOException;
}
