package org.itransition.taskmanager.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExceptionMetadataDto {

    private Date timestamp;
    private String message;
    private int statusCode;
}
