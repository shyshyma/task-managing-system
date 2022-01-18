package org.itransition.taskmanager.services.jpa;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.exceptions.ModelNotFoundException;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.AttachedFileRepository;
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
public class AttachedFileService extends BaseCrudService<AttachedFile, AttachedFileRepository> {

    private static final String ENTITY_NAME = "attached_file";
    private static final String PARENT_ENTITY_NAME = "task";

    @Setter(onMethod_ = @Autowired)
    private TaskService taskService;

    /**
     * Saves attached file entity and sets task parent( by task id)
     * that must have consumer(by consumer id)
     */
    public AttachedFile saveToTaskWithConsumer(AttachedFile attachedFile, Long taskId, Long consumerId) {
        log.info("Saving '" + ENTITY_NAME + "' entity with unique name {} and with task id {}" +
                " and with consumer id {}", attachedFile.getName(), taskId, consumerId);

        Task byIdAndConsumerId = taskService.findByIdAndConsumerId(taskId, consumerId);
        attachedFile.setTask(byIdAndConsumerId);
        return repository.save(attachedFile);
    }

    /**
     * Updates attached file entity, if it has task parent( by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public AttachedFile updateToTaskWithConsumer(AttachedFile attachedFile, Long taskId, Long consumerId) {
        log.info("Updating '" + ENTITY_NAME + "' entity with unique name {} and with task id {}" +
                " and with consumer id {}", attachedFile.getName(), taskId, consumerId);

        AttachedFile attachedFileFromRepo = repository.findByNameAndTaskIdAndTaskConsumerId(attachedFile.getName(), taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException(attachedFile.getName()));
        BeanUtils.copyProperties(attachedFile, attachedFileFromRepo, "id", "task");
        return repository.save(attachedFileFromRepo);
    }

    public <T> List<T> findByTaskIdAndConsumerId(Pageable pageable,
                                                 Long taskId,
                                                 Long consumerId,
                                                 Function<AttachedFile, T> mapper) {

        return findByTaskIdAndConsumerId(taskId, consumerId, pageable).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Finds page of task entities, which belong to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public Page<AttachedFile> findByTaskIdAndConsumerId(Long taskId, Long consumerId, Pageable pageable) {
        log.info("Fetching page of '" + ENTITY_NAME + "' entities who have relationship with '" +
                PARENT_ENTITY_NAME + "' entity with id {} and with 'consumer' with id" +
                " {} from the JPA datastore unit", taskId, consumerId);

        return repository.findByTaskIdAndTaskConsumerId(taskId, consumerId, pageable);
    }

    /**
     * Finds task entity, who belongs to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public AttachedFile findByNameAndTaskIdAndConsumerId(String name, Long taskId, Long consumerId) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with unique name {} and with task id {}" +
                " and with consumer id {}", name, taskId, consumerId);

        return repository.findByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException(name));
    }

    /**
     * Deletes task entity, who belongs to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public void deleteByNameAndTaskIdAndConsumerId(String name, Long taskId, Long consumerId) {
        log.info("Deleting '" + ENTITY_NAME + "' entity with name {} from the" +
                " JPA datastore unit", name);

        if(repository.existsByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId)) {
            throw new ModelNotFoundException(name);
        }

        repository.deleteByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId);
    }
}
