package org.itransition.taskmanager.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.dto.FileMetadataDto;
import org.itransition.taskmanager.mapper.dto.FileMetadataDtoMapper;
import org.itransition.taskmanager.mapper.jpa.TaskJpaMapper;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mapper.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.itransition.taskmanager.jpa.entity.Task;
import org.itransition.taskmanager.jpa.dao.AttachedFileRepository;
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
public class AttachedFileService {

    private static final String ENTITY_NAME = "attached_file";
    private static final String PARENT_ENTITY_NAME = "task";

    private final TaskJpaMapper taskJpaMapper;

    private final FileMetadataDtoMapper fileMetadataDtoMapper;

    private final AttachedFileDtoMapper attachedFileDtoMapper;
    private final AttachedFileJpaMapper attachedFileJpaMapper;

    private final TaskService taskService;

    private final AttachedFileRepository attachedFileRepository;

    /**
     * Saves attached file entity and sets task parent( by task id)
     * that must have consumer(by consumer id)
     */
    public FileMetadataDto saveToTaskWithConsumer(AttachedFileDto attachedFileDto,
                                                  Long taskId,
                                                  Long consumerId) {

        log.info("Saving '" + ENTITY_NAME + "' entity with unique name {} to '" + PARENT_ENTITY_NAME +
                "' entity with id {} and to 'consumer' entity with id {}" +
                " to the JPA datasource unit", attachedFileDto.getName(), taskId, consumerId);

        if (!taskService.existsByIdAndConsumerId(taskId, consumerId)) {
            throw new ModelNotFoundException("there is no '" + PARENT_ENTITY_NAME
                    + "' entity with id " + taskId + ", who has parent" +
                    " 'consumer' entity by id " + consumerId);
        }

        TaskDto byIdAndConsumerId = taskService.findByIdAndConsumerId(taskId, consumerId);
        Task task = taskJpaMapper.map(byIdAndConsumerId);

        AttachedFile attachedFile = attachedFileJpaMapper.map(attachedFileDto);
        attachedFile.setTask(task);
        attachedFileRepository.save(attachedFile);

        return fileMetadataDtoMapper.map(attachedFile,
                "api/consumers/" + consumerId + "/tasks/" + taskId + "/files/"
                        + attachedFileDto.getName());
    }

    /**
     * Updates attached file entity, if it has task parent( by task id)
     * that has relationship with consumer entity(by consumer id)
     */
    public FileMetadataDto updateToTaskWithConsumer(AttachedFileDto attachedFileDto,
                                                    Long taskId,
                                                    Long consumerId) {

        log.info("Saving '" + ENTITY_NAME + "' entity with unique name {} to '" + PARENT_ENTITY_NAME +
                "' entity with id {} and to 'consumer' entity with id {} " +
                "to the JPA datasource unit", attachedFileDto.getName(), taskId, consumerId);

        String fileName = attachedFileDto.getName();
        AttachedFile attachedFileFromRepo = findByNameAndTaskIdAndTaskConsumerIdOrThrow(
                fileName, taskId, consumerId);

        AttachedFile mappedAttachedFile = attachedFileJpaMapper.map(attachedFileDto);
        BeanUtils.copyProperties(mappedAttachedFile, attachedFileFromRepo, "id", "task");
        attachedFileRepository.save(attachedFileFromRepo);

        return fileMetadataDtoMapper.map(attachedFileFromRepo,
                "api/consumers/" + consumerId + "/tasks/" + taskId + "/files/" + fileName);
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

        AttachedFile attachedFile = findByNameAndTaskIdAndTaskConsumerIdOrThrow(name,
                taskId, consumerId);

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

    private AttachedFile findByNameAndTaskIdAndTaskConsumerIdOrThrow(String name,
                                                                     Long taskId,
                                                                     Long consumerId) {

        return attachedFileRepository.findByNameAndTaskIdAndTaskConsumerId(name, taskId, consumerId)
                .orElseThrow(() -> new ModelNotFoundException("no entity '" + ENTITY_NAME + "'," +
                        " was found, who has name " + name + " and belongs to '"
                        + PARENT_ENTITY_NAME + "', 'consumer' entities"));
    }
}
