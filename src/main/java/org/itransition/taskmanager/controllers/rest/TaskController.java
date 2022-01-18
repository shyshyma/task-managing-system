package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.mappers.dto.TaskDtoMapper;
import org.itransition.taskmanager.mappers.jpa.TaskJpaMapper;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.services.jpa.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Setter(onMethod_ = @Autowired)
    private TaskJpaMapper taskJpaMapper;

    @Setter(onMethod_ = @Autowired)
    private TaskDtoMapper taskDtoMapper;

    @Setter(onMethod_ = @Autowired)
    private TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}")
    public TaskDto getConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {
        
        return taskService.findByIdAndConsumerId(taskId, consumerId, taskDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks")
    public List<TaskDto> getConsumerTasks(@PathVariable("consumerId") final Long consumerId,
                                          @PageableDefault(size = 100) final Pageable pageable) {

       return taskService.findByConsumerId(consumerId, pageable, taskDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks")
    public TaskDto saveConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                    @Valid @RequestBody final TaskDto taskDto) {

        Task task = taskJpaMapper.map(taskDto);
        return taskService.saveToConsumer(task, consumerId, taskDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}")
    public TaskDto updateConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                      @PathVariable("taskId") final Long taskId,
                                      @Valid @RequestBody final TaskDto taskDto) {

        Task task = taskJpaMapper.mapWithId(taskDto, taskId);
        return taskService.updateToConsumer(task, consumerId, taskDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}")
    public void deleteConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {

        taskService.deleteByIdAndConsumerId(consumerId, taskId);
    }
}
