package org.itransition.taskmanager.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.mapper.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.exception.DuplicateTitleException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.dto.TaskDtoMapper;
import org.itransition.taskmanager.mapper.jpa.TaskJpaMapper;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.entity.Task;
import org.itransition.taskmanager.jpa.dao.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class TaskDtoService {

    private static final String ENTITY_NAME = "task";
    private static final String PARENT_ENTITY_NAME = "consumer";

    private final TaskDtoMapper taskDtoMapper;
    private final TaskJpaMapper taskJpaMapper;
    private final ConsumerJpaMapper consumerJpaMapper;

    private final ConsumerDtoService consumerDtoService;

    private final TaskRepository taskRepository;

    /**
     * Verifies that task entity exists by 'taskId' and has
     * relationship with consumer by 'consumerId' param
     */
    public boolean existsByIdAndConsumerId(Long taskId, Long consumerId) {
        log.info("Verifying that '" + ENTITY_NAME + "' with id {} exists and belongs to '"
                + PARENT_ENTITY_NAME + "' by id {}", taskId, consumerId);

        return taskRepository.existsByIdAndConsumerId(taskId, consumerId);
    }

    /**
     * Saves task entity and sets consumer parent( by consumer id)
     */
    public TaskDto saveToConsumer(TaskDto taskDto, Long consumerId) {
        String title = taskDto.getTitle();
        log.info("Saving '" + ENTITY_NAME + "' entity with unique title {}" +
                " to consumer with id {} to the JPA datasource unit", title, consumerId);

        if (taskRepository.existsByTitle(title)) {
            throw new DuplicateTitleException("there is already exists '" + ENTITY_NAME
                    + "' entity with title" + title);
        }

        if(!consumerDtoService.existsById(consumerId)) {
            throw new ModelNotFoundException("No entity '" + PARENT_ENTITY_NAME
                    + "' found by id " + consumerId);
        }

        ConsumerDto consumerDto = consumerDtoService.findById(consumerId);
        Consumer consumer = consumerJpaMapper.map(consumerDto);

        Task mappedTask = taskJpaMapper.map(taskDto);
        mappedTask.setConsumer(consumer);
        Task savedTask = taskRepository.save(mappedTask);

        return taskDtoMapper.map(savedTask);
    }

    /**
     * Updates task entity, if it has consumer parent(by consumer id),
     */
    public TaskDto updateByIdAndConsumerId(Long id, Long consumerId, TaskDto taskDto) {
        log.info("Updating '" + ENTITY_NAME + "' entity with unique title {} " +
                "for consumer with id {} to the JPA datasource unit", taskDto.getTitle(), consumerId);

        Task taskFromRepo = taskRepository.findByIdAndConsumerId(id, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("there is no '" + ENTITY_NAME
                        + "' entity with id " + id + ", who has parent" +
                        " '" + PARENT_ENTITY_NAME + "' entity with id " + consumerId));

        BeanUtils.copyProperties(taskDto, taskFromRepo, "id", "creationDate", "consumer");
        Task savedTask = taskRepository.save(taskFromRepo);

        return taskDtoMapper.map(savedTask);
    }

    /**
     * Finds task entity, if it belongs to consumer parent(by consumer id)
     */
    public TaskDto findByIdAndConsumerId(Long id, Long consumerId) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with id {} who have relationship with '" +
                PARENT_ENTITY_NAME + "' entity with id {} from the JPA datasource unit", id, consumerId);

        Task taskByIdAndConsumerId = taskRepository.findByIdAndConsumerId(id, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("there is no '" + ENTITY_NAME
                        + "' entity with id " + id + ", who has parent" +
                        " '" + PARENT_ENTITY_NAME + "' entity with id " + consumerId));

        return taskDtoMapper.map(taskByIdAndConsumerId);
    }

    /**
     * Finds task entities, that belongs to consumer parent(by consumer id)
     */
    public List<TaskDto> findByConsumerId(Long consumerId, Pageable pageable) {
        log.info("Fetching '" + ENTITY_NAME + "' entities who have relationship to '" +
                PARENT_ENTITY_NAME + "' parent entity with id {} from" +
                " the JPA datastore unit", consumerId);

        return taskRepository.findByConsumerId(consumerId, pageable).stream()
                .map(taskDtoMapper::map)
                .collect(Collectors.toList());
    }

    /**
     * Delete task entity, if it has consumer parent(by consumer id)
     */
    public void deleteByIdAndConsumerId(Long id, Long consumerId) {
        log.info("Deleting '" + ENTITY_NAME + "' entity by id {}, who has" +
                " relationship to '" + PARENT_ENTITY_NAME + "' parent entity by id {} from" +
                " the JPA datastore unit", id, consumerId);

        if (!taskRepository.existsByIdAndConsumerId(id, consumerId)) {
            throw new ModelNotFoundException("there is no '" + ENTITY_NAME
                    + "' entity with id " + id + ", who has parent" +
                    " '" + PARENT_ENTITY_NAME + "' entity by id " + consumerId);
        }

        taskRepository.deleteByIdAndConsumerId(id, consumerId);
    }
}
