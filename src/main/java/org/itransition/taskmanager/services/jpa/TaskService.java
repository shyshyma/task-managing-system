package org.itransition.taskmanager.services.jpa;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.exceptions.DuplicateTitleException;
import org.itransition.taskmanager.exceptions.ModelNotFoundException;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService extends BaseCrudService<Task, TaskRepository> {

    private static final String ENTITY_NAME = "task";
    private static final String PARENT_ENTITY_NAME = "consumer";

    @Setter(onMethod_ = @Autowired)
    private ConsumerService consumerService;

    public <T> T saveToConsumer(Task task, Long consumerId, Function<Task, T> mapper) {
        return mapper.apply(saveToConsumer(task, consumerId));
    }

    /**
     * Saves task entity and sets consumer parent( by consumer id)
     */
    public Task saveToConsumer(Task task, Long consumerId) {
        String title = task.getTitle();

        log.info("Saving '" + ENTITY_NAME + "' entity with unique title {}" +
                " to consumer with id {}", title, consumerId);

        if (repository.existsByTitle(title)) {
            throw new DuplicateTitleException("there is already exists '" + ENTITY_NAME
                    + "' entity with title" + title);
        }

        Consumer consumer = consumerService.findById(consumerId);
        task.setConsumer(consumer);
        return repository.save(task);
    }

    public <T> T updateToConsumer(Task task, Long consumerId, Function<Task, T> mapper) {
        return mapper.apply(updateToConsumer(task, consumerId));
    }

    /**
     * Updates task entity, if it has consumer parent(by consumer id),
     */
    public Task updateToConsumer(Task task, Long consumerId) {
        log.info("Updating '" + ENTITY_NAME + "' entity with unique title {} " +
                "for consumer with id {}", task.getTitle(), consumerId);

        Long taskId = task.getId();
        Task taskFromRepo = repository.findByIdAndConsumerId(taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("there is no '" + ENTITY_NAME
                        + "' entity with id " + taskId + ", who has parent" +
                        " '" + PARENT_ENTITY_NAME + "' entity with id " + consumerId));
        BeanUtils.copyProperties(task, taskFromRepo, "id", "creationDate", "consumer");
        return repository.save(taskFromRepo);
    }

    public <T> T findByIdAndConsumerId(Long id, Long consumerId, Function<Task, T> mapper) {
        return mapper.apply(findByIdAndConsumerId(id, consumerId));
    }

    /**
     * Finds task entity, if it belongs to consumer parent(by consumer id)
     */
    public Task findByIdAndConsumerId(Long id, Long consumerId) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with id {} who have relationship with '" +
                PARENT_ENTITY_NAME + "' entity with id {}", id, consumerId);

        return repository.findByIdAndConsumerId(id, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("there is no '" + ENTITY_NAME
                        + "' entity with id " + id + ", who has parent" +
                        " '" + PARENT_ENTITY_NAME + "' entity with id " + consumerId));
    }

    public <T> List<T> findByConsumerId(Long consumerId,
                                        Pageable pageable,
                                        Function<Task, T> mapper) {

        return findPageByConsumerId(consumerId, pageable).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Finds task entities, that belongs to consumer parent(by consumer id)
     */
    public Page<Task> findPageByConsumerId(Long consumerId, Pageable pageable) {
        log.info("Fetching page of '" + ENTITY_NAME + "' entities who have relationship to '" +
                PARENT_ENTITY_NAME + "' parent entity with id {} from" +
                " the JPA datastore unit", consumerId);

        return repository.findByConsumerId(consumerId, pageable);
    }

    /**
     * Delete task entity, if it has consumer parent(by consumer id)
     */
    public void deleteByIdAndConsumerId(Long id, Long consumerId) {
        log.info("Deleting '" + ENTITY_NAME + "' entity with name {} from the" +
                " JPA datastore unit", id);

        if (!repository.existsByIdAndConsumerId(id, consumerId)) {
            throw new ModelNotFoundException("there is no '" + ENTITY_NAME
                    + "' entity with id " + id + ", who has parent" +
                    " '" + PARENT_ENTITY_NAME + "' entity with id " + consumerId);
        }

        repository.deleteByIdAndConsumerId(id, consumerId);
    }
}
