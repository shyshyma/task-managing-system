package org.itransition.taskmanager.dtos.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.itransition.taskmanager.dtos.Dto;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto implements Dto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(value = "title", access = JsonProperty.Access.READ_WRITE)
    private String title;

    @JsonProperty(value = "description", access = JsonProperty.Access.READ_WRITE)
    private String description;

    @JsonProperty(value = "creationDate", access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    @JsonProperty(value = "expirationDate", access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expirationDate;

    @JsonProperty(value = "donePercentage", access = JsonProperty.Access.READ_WRITE)
    private Byte donePercentage;

    @JsonProperty(value = "priority", access = JsonProperty.Access.READ_WRITE)
    private String priority;

    @JsonProperty(value = "status", access = JsonProperty.Access.READ_WRITE)
    private String status;
}
