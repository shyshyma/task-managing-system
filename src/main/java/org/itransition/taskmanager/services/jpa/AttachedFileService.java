package org.itransition.taskmanager.services.jpa;

import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.AttachedFileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public class AttachedFileService extends BaseCrudService<AttachedFile, AttachedFileRepository> {

    private static final String ENTITY_NAME = "attached_file";
    private static final String PARENT_ENTITY_NAME = "task";


    public boolean existsByName(String name) {
        log.info("Verifying that '" + ENTITY_NAME + "' entity with name" +
                " {} exists inside of the JPA datastore unit", name);
        return repository.existsByName(name);
    }

    public AttachedFile findByName(String name) {
        log.info("Fetching '" + ENTITY_NAME + "' entity" + " with name {} from" +
                " the JPA datastore unit", name);
        return repository.findByName(name);
    }

    public <T> T findByName(String name, Function<AttachedFile, T> mapper) {
        log.info("Fetching '" + ENTITY_NAME + "' entity" +
                " with name {} from the JPA datastore unit", name);
        return mapper.apply(repository.findByName(name));
    }

    public void deleteByName(String name) {
        log.info("Deleting '" + ENTITY_NAME + "' entity with name {} from the" +
                " JPA datastore unit", name);
        repository.deleteByName(name);
    }

    public Page<AttachedFile> findPageByTaskId(Long taskId, Pageable pageable) {
        log.info("Fetching page of '" + ENTITY_NAME + "' entities who have relationship to '" +
                PARENT_ENTITY_NAME + "' parent entity with id {} from the JPA datastore unit", taskId);
        return repository.findByTaskId(taskId, pageable);
    }

    /**
     * Verifies that 'attached_file' entity exists and it also has relationship
     * with 'task' table
     */
    public boolean existsAndBelongsToTask(String attachedFileName, Long taskId) {

        log.info("Verifying that '" + ENTITY_NAME + "' entity with unique name {} exists" +
                        " and definitely belongs to '" + PARENT_ENTITY_NAME + "' entity with id {}",
                attachedFileName, taskId);

        if (!repository.existsByName(attachedFileName)) {
            log.error("'" + PARENT_ENTITY_NAME + "' entity with unique name {} doesn't" +
                    " exists in JPA database unit", attachedFileName);
            return false;
        }

        Task taskByFileName = findByName(attachedFileName).getTask();
        boolean belongsToTask = Objects.equals(taskByFileName.getId(), taskId);

        if (!belongsToTask) {
            log.error("'" + PARENT_ENTITY_NAME + "' entity with unique name {} doesn't belongs to" +
                    " '" + PARENT_ENTITY_NAME + "' in JPA database unit", attachedFileName);
        }

        return belongsToTask;
    }
}
