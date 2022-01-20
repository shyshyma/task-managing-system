package org.itransition.taskmanager.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.models.dto.TaskDto;
import org.itransition.taskmanager.services.dto.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}")
    public TaskDto getConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {

        return taskService.findByIdAndConsumerId(taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks")
    public List<TaskDto> getConsumerTasks(@PathVariable("consumerId") final Long consumerId,
                                          @PageableDefault(size = 100) final Pageable pageable) {

        return taskService.findByConsumerId(consumerId, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks")
    public TaskDto saveConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                    @Valid @RequestBody final TaskDto taskDto) {

        return taskService.saveToConsumer(taskDto, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}")
    public TaskDto updateConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                      @PathVariable("taskId") final Long taskId,
                                      @Valid @RequestBody final TaskDto taskDto) {
        
        return taskService.updateByIdAndConsumerId(taskId, consumerId, taskDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}")
    public void deleteConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {

        taskService.deleteByIdAndConsumerId(consumerId, taskId);
    }
}
