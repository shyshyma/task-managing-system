package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.commons.web.HttpUtilsBean;
import org.itransition.taskmanager.dtos.jpa.AttachedFileDto;
import org.itransition.taskmanager.exceptions.DuplicateNameException;
import org.itransition.taskmanager.exceptions.ResourceNotFoundException;
import org.itransition.taskmanager.mappers.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mappers.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.services.jpa.AttachedFileService;
import org.itransition.taskmanager.services.jpa.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/consumers")
public class AttachedFileController {

    @Setter(onMethod_ = @Autowired)
    private TaskService taskService;

    @Setter(onMethod_ = @Autowired)
    private AttachedFileService attachedFileService;

    @Setter(onMethod_ = @Autowired)
    private AttachedFileJpaMapper attachedFileJpaMapper;

    @Setter(onMethod_ = @Autowired)
    private AttachedFileDtoMapper attachedFileDtoMapper;

    @Setter(onMethod_ = @Autowired)
    private HttpUtilsBean httpUtilsBean;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/attached-files/{attachedFileName}")
    public void getConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                            @PathVariable("taskId") final Long taskId,
                                            @PathVariable("attachedFileName") final String filename,
                                            final HttpServletResponse response) throws IOException {

        if (!(taskService.existsAndBelongsToConsumer(taskId, consumerId)
                || attachedFileService.existsAndBelongsToTask(filename, taskId))) {
            throw new ResourceNotFoundException("No resource");
        }

        AttachedFileDto fileDto = attachedFileService.findByName(filename, attachedFileDtoMapper::map);
        httpUtilsBean.writeFileToResponse(fileDto, response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/attached-files")
    public void getConsumerTaskAttachedFiles(@PathVariable("consumerId") final Long consumerId,
                                             @PathVariable("taskId") final Long taskId,
                                             @PageableDefault(size = 100) final Pageable pageable,
                                             final HttpServletResponse response) throws IOException {

        if (!taskService.existsAndBelongsToConsumer(taskId, consumerId)) {
            throw new ResourceNotFoundException();
        }

        List<AttachedFileDto> result = attachedFileService.findPageByTaskId(taskId, pageable).stream()
                .map(attachedFileDtoMapper::map)
                .toList();
        httpUtilsBean.writeZipFileToResponse(result, response, "data.zip");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks/{taskId}/attached-files")
    public Map<String, String> saveConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                            @PathVariable("taskId") final Long taskId,
                                                            @RequestParam("file") final MultipartFile file) throws IOException {

        if (!taskService.existsAndBelongsToConsumer(taskId, consumerId)) {
            throw new ResourceNotFoundException();
        }

        String originalFilename = file.getOriginalFilename();
        if (attachedFileService.existsByName(originalFilename)) {
            throw new DuplicateNameException("'attached_file' entry with name '" + originalFilename +
                    "' already exists in DB");
        }

        AttachedFile attachedFile = attachedFileJpaMapper.map(file, taskId);
        attachedFileService.save(attachedFile);

        return Map.of("fileName", attachedFile.getName(), "size", "" + attachedFile.getData().length,
                "downloadPath", "api/consumers/" + consumerId + "/tasks" + taskId + "/attached-files/"
                        + attachedFile.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}/attached-files")
    public Map<String, String> updateConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                              @PathVariable("taskId") final Long taskId,
                                                              @RequestParam("file") final MultipartFile file) throws IOException {

        if (!(taskService.existsAndBelongsToConsumer(taskId, consumerId)
                || attachedFileService.existsAndBelongsToTask(file.getOriginalFilename(), taskId))) {
            throw new ResourceNotFoundException();
        }

        AttachedFile attachedFile = attachedFileJpaMapper.map(file, taskId);
        attachedFileService.update(attachedFile);

        return Map.of("fileName", attachedFile.getName(), "size", "" + attachedFile.getData().length,
                "downloadPath", "api/consumers/" + consumerId + "/tasks" + taskId + "/attached-files/"
                        + attachedFile.getName());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}/attached-files/{attachedFileName}")
    public void deleteConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                               @PathVariable("taskId") final Long taskId,
                                               @PathVariable("attachedFileName") final String filename) {

        if (!(taskService.existsAndBelongsToConsumer(taskId, consumerId)
                || attachedFileService.existsAndBelongsToTask(filename, taskId))) {
            throw new ResourceNotFoundException();
        }

        attachedFileService.deleteByName(filename);
    }
}
