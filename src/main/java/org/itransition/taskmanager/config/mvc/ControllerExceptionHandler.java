package org.itransition.taskmanager.config.mvc;

import org.itransition.taskmanager.exceptions.DuplicateNameException;
import org.itransition.taskmanager.exceptions.ModelNotFoundException;
import org.itransition.taskmanager.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ModelNotFoundException.class)
    public Map<String, Object> handle(ModelNotFoundException exception) {
        return generateResponse(exception, HttpStatus.NOT_FOUND.value());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateNameException.class)
    public Map<String, Object> handle(DuplicateNameException exception) {
        return generateResponse(exception, HttpStatus.BAD_REQUEST.value());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public Map<String, Object> handle(ResourceNotFoundException exception) {
        return generateResponse(exception, HttpStatus.NOT_FOUND.value());
    }


    private Map<String, Object> generateResponse(Exception ex, int statusCode) {
        return Map.of("timestamp", LocalDate.now(),
                "message", ex.getMessage(),
                "statusCode", statusCode);
    }

}
