package org.itransition.taskmanager.config.mvc;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.ExceptionMetadataDto;
import org.itransition.taskmanager.exception.DuplicateFileNameException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.exception.DuplicateTitleException;
import org.itransition.taskmanager.mapper.ExceptionDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final ExceptionDtoMapper exceptionDtoMapper;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ModelNotFoundException.class)
    public ExceptionMetadataDto handleModelNotFoundException(ModelNotFoundException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateEmailException.class)
    public ExceptionMetadataDto handleDuplicateEmailException(DuplicateEmailException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateTitleException.class)
    public ExceptionMetadataDto handleDuplicateTitleException(DuplicateTitleException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateFileNameException.class)
    public ExceptionMetadataDto handleDuplicateFileNameException(DuplicateFileNameException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }
}
