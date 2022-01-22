package org.itransition.taskmanager.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.models.dto.AttachedFileDto;
import org.itransition.taskmanager.service.dto.AttachedFileDtoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachedFileController {

    private final AttachedFileDtoService attachedFileDtoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public AttachedFileDto getConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                       @PathVariable("taskId") final Long taskId,
                                                       @PathVariable("attachedFileName") final String filename) {

        return attachedFileDtoService.findByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/files")
    public List<AttachedFileDto> getConsumerTaskAttachedFiles(@PathVariable("consumerId") final Long consumerId,
                                                              @PathVariable("taskId") final Long taskId,
                                                              @PageableDefault(size = 100) final Pageable pageable) {

        return attachedFileDtoService.findByTaskIdAndConsumerId(taskId, consumerId, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks/{taskId}/files")
    public AttachedFileDto saveConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                        @PathVariable("taskId") final Long taskId,
                                                        @RequestParam("file") final AttachedFileDto fileDto) {

        return attachedFileDtoService.saveToTaskWithConsumer(fileDto, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}/files")
    public AttachedFileDto updateConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                          @PathVariable("taskId") final Long taskId,
                                                          @RequestParam("file") final AttachedFileDto fileDto) {

        return attachedFileDtoService.updateToTaskWithConsumer(fileDto, taskId, consumerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public void deleteConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                               @PathVariable("taskId") final Long taskId,
                                               @PathVariable("attachedFileName") final String filename) {

        attachedFileDtoService.deleteByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }
}
