package org.itransition.taskmanager.controller.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers/{consumerId}/tasks",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    @GetMapping("{taskId}")
    public TaskDto getConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {

        return taskService.findByIdAndConsumerId(taskId, consumerId);
    }

    @GetMapping
    public List<TaskDto> getConsumerTasks(@PathVariable("consumerId") final Long consumerId,
                                          @PageableDefault(size = 100) final Pageable pageable) {

        return taskService.findByConsumerId(consumerId, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDto saveConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                    @Valid @RequestBody final TaskDto taskDto) {

        return taskService.saveToConsumer(taskDto, consumerId);
    }

    @PutMapping("{taskId}")
    public TaskDto updateConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                      @PathVariable("taskId") final Long taskId,
                                      @Valid @RequestBody final TaskDto taskDto) {

        return taskService.updateByIdAndConsumerId(taskId, consumerId, taskDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{taskId}")
    public void deleteConsumerTask(@PathVariable("consumerId") final Long consumerId,
                                   @PathVariable("taskId") final Long taskId) {

        taskService.deleteByIdAndConsumerId(consumerId, taskId);
    }
}
