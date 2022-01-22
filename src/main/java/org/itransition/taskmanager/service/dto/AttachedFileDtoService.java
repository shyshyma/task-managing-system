package org.itransition.taskmanager.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.mappers.jpa.TaskJpaMapper;
import org.itransition.taskmanager.models.dto.AttachedFileDto;
import org.itransition.taskmanager.exceptions.ModelNotFoundException;
import org.itransition.taskmanager.mappers.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mappers.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.models.dto.TaskDto;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.AttachedFileRepository;
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
public class AttachedFileDtoService {

    private static final String ENTITY_NAME = "attached_file";
    private static final String PARENT_ENTITY_NAME = "task";

    private final TaskJpaMapper taskJpaMapper;

    private final AttachedFileDtoMapper attachedFileDtoMapper;
    private final AttachedFileJpaMapper attachedFileJpaMapper;

    private final TaskDtoService taskDtoService;

    private final AttachedFileRepository attachedFileRepository;

    /**
     * Saves attached file entity and sets task parent( by task id)
     * that must have consumer(by consumer id)
     */
    public AttachedFileDto saveToTaskWithConsumer(AttachedFileDto attachedFileDto,
                                                  Long taskId,
                                                  Long consumerId) {

        log.info("Saving '" + ENTITY_NAME + "' entity with unique name {} to '" + PARENT_ENTITY_NAME +
                "' entity with id {} and to 'consumer' entity with id {}" +
                " to the JPA datasource unit", attachedFileDto.getName(), taskId, consumerId);

        if (!taskDtoService.existsByIdAndConsumerId(taskId, consumerId)) {
            throw new ModelNotFoundException("there is no '" + PARENT_ENTITY_NAME
                    + "' entity with id " + taskId + ", who has parent" +
                    " 'consumer' entity by id " + consumerId);
        }

        TaskDto byIdAndConsumerId = taskDtoService.findByIdAndConsumerId(taskId, consumerId);
        Task task = taskJpaMapper.map(byIdAndConsumerId);

        AttachedFile attachedFile = attachedFileJpaMapper.map(attachedFileDto);
        attachedFile.setTask(task);
        attachedFileRepository.save(attachedFile);

        return attachedFileDtoMapper.map(attachedFile);
    }

    /**
     * Updates attached file entity, if it has task parent( by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public AttachedFileDto updateToTaskWithConsumer(AttachedFileDto attachedFileDto,
                                                    Long taskId,
                                                    Long consumerId) {

        log.info("Saving '" + ENTITY_NAME + "' entity with unique name {} to '" + PARENT_ENTITY_NAME +
                "' entity with id {} and to 'consumer' entity with id {} " +
                "to the JPA datasource unit", attachedFileDto.getName(), taskId, consumerId);

        String fileName = attachedFileDto.getName();
        AttachedFile attachedFileFromRepo = attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(fileName, taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("no entity '" + ENTITY_NAME + "', was found" +
                        " who has name " + fileName + " and belongs to '" + PARENT_ENTITY_NAME + "', " +
                        " 'consumer' entities"));

        AttachedFile mappedAttachedFile = attachedFileJpaMapper.map(attachedFileDto);
        BeanUtils.copyProperties(mappedAttachedFile, attachedFileFromRepo, "id", "task");
        attachedFileRepository.save(attachedFileFromRepo);

        return attachedFileDtoMapper.map(attachedFileFromRepo);
    }

    /**
     * Finds page of task entities, which belong to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public List<AttachedFileDto> findByTaskIdAndConsumerId(Long taskId,
                                                           Long consumerId,
                                                           Pageable pageable) {

        log.info("Fetching '" + ENTITY_NAME + "' entities who have relationship with '" +
                PARENT_ENTITY_NAME + "' entity with id {} and with 'consumer' with id" +
                " {} from the JPA datastore unit", taskId, consumerId);

        return attachedFileRepository.findByTaskIdAndTaskConsumerId(taskId, consumerId, pageable).stream()
                .map(attachedFileDtoMapper::map)
                .collect(Collectors.toList());
    }

    /**
     * Finds task entity, who belongs to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public AttachedFileDto findByNameAndTaskIdAndConsumerId(String name, Long taskId, Long consumerId) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with unique name {}, which attached with '" +
                PARENT_ENTITY_NAME + "' entity by id {} and with 'consumer' entity by id {}" +
                " from the JPA datasource unit", name, taskId, consumerId);

        AttachedFile attachedFile = attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("no entity '" + ENTITY_NAME + "'," +
                        " was found, who has name " + name + " and belongs to '"
                        + PARENT_ENTITY_NAME + "', 'consumer' entities"));

        return attachedFileDtoMapper.map(attachedFile);
    }

    /**
     * Deletes task entity, who belongs to task parent(by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public void deleteByNameAndTaskIdAndConsumerId(String name, Long taskId, Long consumerId) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with unique name {}, which attached with '" +
                PARENT_ENTITY_NAME + "' entity by id {} and with 'consumer' entity by id {}" +
                " from the JPA datasource unit", name, taskId, consumerId);

        if (!attachedFileRepository.existsByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId)) {
            throw new ModelNotFoundException("no entity '" + ENTITY_NAME + "', was found" +
                    " who has name " + name + " and belongs to '"
                    + PARENT_ENTITY_NAME + "', 'consumer' entities");
        }

        attachedFileRepository.deleteByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId);
    }
}
