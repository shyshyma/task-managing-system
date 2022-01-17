package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.TaskDto;
import org.itransition.taskmanager.exceptions.ResourceNotFoundException;
import org.itransition.taskmanager.mappers.dto.TaskDtoMapper;
import org.itransition.taskmanager.mappers.jpa.TaskJpaMapper;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.services.jpa.ConsumerService;
import org.itransition.taskmanager.services.jpa.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Setter(onMethod_ = @Autowired)
    private TaskService taskService;

    @Setter(onMethod_ = @Autowired)
    private ConsumerService consumerService;

    @Setter(onMethod_ = @Autowired)
    private TaskJpaMapper taskJpaMapper;
    
    @Setter(onMethod_ = @Autowired)
    private TaskDtoMapper taskDtoMapper;

    @GetMapping("{consumerId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                                   @PathVariable("taskId") final Long taskId) {

        return !taskService.existsAndBelongsToConsumer(taskId, consumerId)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(taskDtoMapper.map(taskService.findById(taskId)), HttpStatus.OK);
    }

    @GetMapping("{consumerId}/tasks")
    public ResponseEntity<List<TaskDto>> getConsumerTasks(@PathVariable("consumerId") final Long consumerId,
                                                          @PageableDefault(size = 100) final Pageable pageable) {

        List<TaskDto> taskDtos = taskService.findPageByConsumerId(consumerId, pageable).stream()
                .map(taskDtoMapper::map)
                .collect(Collectors.toList());

        return (taskDtos.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    @PostMapping("{consumerId}/tasks")
    public ResponseEntity<TaskDto> saveConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                                    @Valid @RequestBody final TaskDto taskDto) {

        if (!consumerService.existsById(consumerId)) {
            throw  new ResourceNotFoundException();
        }

        taskService.save(taskJpaMapper.map(taskDto, consumerId));
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @PutMapping("{consumerId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                                      @PathVariable("taskId") final Long taskId,
                                                      @Valid @RequestBody final TaskDto taskDto) {

        if (!taskService.existsAndBelongsToConsumer(taskId, consumerId)) {
            throw new ResourceNotFoundException("No resource was found");
        }

        Task mappedTask = taskJpaMapper.map(taskDto, taskId, consumerId);
        taskService.update(mappedTask, "id", "creationDate");
        return new ResponseEntity<>(taskDtoMapper.map(mappedTask), HttpStatus.OK);
    }

    @DeleteMapping("{consumerId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> deleteConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                                      @PathVariable("taskId") final Long taskId) {

        if (!consumerService.existsById(consumerId)) {
            throw new ResourceNotFoundException();
        }

        taskService.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }
}
