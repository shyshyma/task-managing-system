package org.itransition.taskmanager.services.jpa;

import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TaskService extends BaseCrudService<Task, TaskRepository> {

    private static final String ENTITY_NAME = "task";
    private static final String PARENT_ENTITY_NAME = "consumer";


    public Page<Task> findPageByConsumerId(Long consumerId, Pageable pageable) {
        log.info("Fetching page of '" + ENTITY_NAME + "' entities who have relationship to '" +
                PARENT_ENTITY_NAME + "' parent entity with id {} from the JPA datastore unit", consumerId);
        return repository.findByConsumerId(consumerId, pageable);
    }

    /**
     * Verifies that 'task' entity exists and it also has
     * relationship with 'customer' table
     */
    public boolean existsAndBelongsToConsumer(Long id, Long consumerId) {
        log.info("Verifying that '" + ENTITY_NAME + "' entity with id {} exists and" +
                " definitely belongs to '"
                + PARENT_ENTITY_NAME + "' entity with id {}", id, consumerId);

        if (!repository.existsById(id)) {
            log.error("'" + ENTITY_NAME + "' entity with id {} doesn't exists in" +
                    "JPA  database unit", id);
            return false;
        }

        Consumer consumerByTaskId = super.findById(id).getConsumer();
        boolean belongsToConsumer = Objects.equals(consumerByTaskId.getId(), consumerId);

        if (!belongsToConsumer) {
            log.error("'" + ENTITY_NAME + "' JPA entity with id {} doesn't belongs to '"
                    + PARENT_ENTITY_NAME + "' in database unit", id);
        }

        return belongsToConsumer;
    }
}
