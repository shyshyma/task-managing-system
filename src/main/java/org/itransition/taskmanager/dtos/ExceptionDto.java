package org.itransition.taskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExceptionDto {

    private LocalDate timestamp;
    private String message;
    private int statusCode;
}
