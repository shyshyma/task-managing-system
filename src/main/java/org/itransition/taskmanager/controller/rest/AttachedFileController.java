package org.itransition.taskmanager.controller.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.dto.FileMetadataDto;
import org.itransition.taskmanager.service.dto.AttachedFileService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers/{consumerId}/tasks/{taskId}/files",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AttachedFileController {

    private final AttachedFileService attachedFileService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{attachedFileName}")
    public AttachedFileDto getConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                       @PathVariable("taskId") final Long taskId,
                                                       @PathVariable("attachedFileName") final String filename) {

        return attachedFileService.findByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AttachedFileDto> getConsumerTaskAttachedFiles(@PathVariable("consumerId") final Long consumerId,
                                                              @PathVariable("taskId") final Long taskId,
                                                              @PageableDefault(size = 100) final Pageable pageable) {

        return attachedFileService.findByTaskIdAndConsumerId(taskId, consumerId, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FileMetadataDto saveConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                        @PathVariable("taskId") final Long taskId,
                                                        @RequestBody final AttachedFileDto fileDto) {

        return attachedFileService.saveToTaskWithConsumer(fileDto, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public FileMetadataDto updateConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                          @PathVariable("taskId") final Long taskId,
                                                          @RequestBody final AttachedFileDto fileDto) {

        return attachedFileService.updateToTaskWithConsumer(fileDto, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{attachedFileName}")
    public void deleteConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                               @PathVariable("taskId") final Long taskId,
                                               @PathVariable("attachedFileName") final String filename) {

        attachedFileService.deleteByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }
}
