package org.itransition.taskmanager.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class ExceptionMetadataDto implements Dto {

    private Date timestamp;
    private String message;
    private int statusCode;
}
