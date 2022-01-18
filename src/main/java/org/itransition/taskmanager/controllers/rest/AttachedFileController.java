package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.commons.web.HttpUtilsBean;
import org.itransition.taskmanager.dtos.FileDto;
import org.itransition.taskmanager.dtos.jpa.AttachedFileDto;
import org.itransition.taskmanager.mappers.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mappers.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.services.jpa.AttachedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/consumers")
public class AttachedFileController {

    @Setter(onMethod_ = @Autowired)
    private AttachedFileJpaMapper attachedFileJpaMapper;

    @Setter(onMethod_ = @Autowired)
    private AttachedFileDtoMapper attachedFileDtoMapper;

    @Setter(onMethod_ = @Autowired)
    private AttachedFileService attachedFileService;

    @Setter(onMethod_ = @Autowired)
    private HttpUtilsBean httpUtilsBean;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public void getConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                            @PathVariable("taskId") final Long taskId,
                                            @PathVariable("attachedFileName") final String filename,
                                            final HttpServletResponse response) throws IOException {

        AttachedFile file = attachedFileService.findByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
        httpUtilsBean.writeFileToResponse(attachedFileDtoMapper.map(file), response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{consumerId}/tasks/{taskId}/files")
    public void getConsumerTaskAttachedFiles(@PathVariable("consumerId") final Long consumerId,
                                             @PathVariable("taskId") final Long taskId,
                                             @PageableDefault(size = 100) final Pageable pageable,
                                             final HttpServletResponse response) throws IOException {

        List<AttachedFileDto> result = attachedFileService
                .findByTaskIdAndConsumerId(pageable, taskId, consumerId, attachedFileDtoMapper::map);

        httpUtilsBean.writeZipFileToResponse(result, response, "data.zip");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{consumerId}/tasks/{taskId}/files")
    public FileDto saveConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                @PathVariable("taskId") final Long taskId,
                                                @RequestParam("file") final MultipartFile file) throws IOException {

        AttachedFile attachedFile = attachedFileJpaMapper.map(file);
        attachedFileService.saveToTaskWithConsumer(attachedFile, taskId, consumerId);

        return FileDto.builder()
                .downloadPath("api/consumers/" + consumerId + "/tasks" + taskId + "/attached-files/")
                .fileName(attachedFile.getName())
                .size(attachedFile.getData().length)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}/tasks/{taskId}/files")
    public FileDto updateConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                                  @PathVariable("taskId") final Long taskId,
                                                  @RequestParam("file") final MultipartFile file) throws IOException {

        AttachedFile attachedFile = attachedFileJpaMapper.map(file);
        attachedFileService.updateToTaskWithConsumer(attachedFile, taskId, consumerId);

        return FileDto.builder()
                .downloadPath("api/consumers/" + consumerId + "/tasks" + taskId + "/attached-files/")
                .fileName(attachedFile.getName())
                .size(attachedFile.getData().length)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{consumerId}/tasks/{taskId}/files/{attachedFileName}")
    public void deleteConsumerTaskAttachedFile(@PathVariable("consumerId") final Long consumerId,
                                               @PathVariable("taskId") final Long taskId,
                                               @PathVariable("attachedFileName") final String filename) {

        attachedFileService.deleteByNameAndTaskIdAndConsumerId(filename, taskId, consumerId);
    }
}
