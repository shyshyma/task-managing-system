package org.itransition.taskmanager.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class ExceptionMetadataDto {

    private Date timestamp;
    private String message;
    private int statusCode;
}
