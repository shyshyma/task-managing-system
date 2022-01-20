package org.itransition.taskmanager.config.mvc;

import lombok.Setter;
import org.itransition.taskmanager.models.dto.ExceptionMetadataDto;
import org.itransition.taskmanager.exceptions.*;
import org.itransition.taskmanager.mappers.dto.ExceptionDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Setter(onMethod_ = @Autowired)
    private ExceptionDtoMapper exceptionDtoMapper;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ModelNotFoundException.class)
    public ExceptionMetadataDto handle(ModelNotFoundException exception) {
       return exceptionDtoMapper.map(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateNameException.class)
    public ExceptionMetadataDto handle(DuplicateNameException exception) {
       return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateEmailException.class)
    public ExceptionMetadataDto handle(DuplicateEmailException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateTitleException.class)
    public ExceptionMetadataDto handle(DuplicateTitleException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ExceptionMetadataDto handle(ResourceNotFoundException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.NOT_FOUND);
    }
}
