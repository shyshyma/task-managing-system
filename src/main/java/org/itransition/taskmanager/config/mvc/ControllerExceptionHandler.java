package org.itransition.taskmanager.config.mvc;

import lombok.Setter;
import org.itransition.taskmanager.dtos.ExceptionDto;
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
    public ExceptionDto handle(ModelNotFoundException exception) {
       return exceptionDtoMapper.map(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateNameException.class)
    public ExceptionDto handle(DuplicateNameException exception) {
       return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateEmailException.class)
    public ExceptionDto handle(DuplicateEmailException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateTitleException.class)
    public ExceptionDto handle(DuplicateTitleException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ExceptionDto handle(ResourceNotFoundException exception) {
        return exceptionDtoMapper.map(exception, HttpStatus.NOT_FOUND);
    }
}
