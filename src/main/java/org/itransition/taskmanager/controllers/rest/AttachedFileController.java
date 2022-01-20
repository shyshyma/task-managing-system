package org.itransition.taskmanager.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.models.dto.FileMetadataDto;
import org.itransition.taskmanager.models.dto.AttachedFileDto;
import org.itransition.taskmanager.services.dto.AttachedFileService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers")
public class AttachedFileController {

    private final AttachedFileService attachedFileService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public AttachedFileDto getConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                       @PathVariable("taskId") final Long taskId,
                                                       @PathVariable("attachedFileName") final String filename) {

        return attachedFileService.findByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/files")
    public List<AttachedFileDto> getConsumerTaskAttachedFiles(@PathVariable("consumerId") final Long consumerId,
                                                              @PathVariable("taskId") final Long taskId,
                                                              @PageableDefault(size = 100) final Pageable pageable) {

        return attachedFileService.findByTaskIdAndConsumerId(taskId, consumerId, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks/{taskId}/files")
    public FileMetadataDto saveConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                        @PathVariable("taskId") final Long taskId,
                                                        @RequestParam("file") final MultipartFile file) throws IOException {

        return attachedFileService.saveToTaskWithConsumer(file, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}/files")
    public FileMetadataDto updateConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                          @PathVariable("taskId") final Long taskId,
                                                          @RequestParam("file") final MultipartFile file) throws IOException {

        return attachedFileService.updateToTaskWithConsumer(taskId, consumerId, file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public void deleteConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                               @PathVariable("taskId") final Long taskId,
                                               @PathVariable("attachedFileName") final String filename) {

        attachedFileService.deleteByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }
}
