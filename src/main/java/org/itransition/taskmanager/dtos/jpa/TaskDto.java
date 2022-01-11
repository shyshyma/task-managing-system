package org.itransition.taskmanager.dtos.jpa;

import lombok.*;
import org.itransition.taskmanager.dtos.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto implements Dto {

    private Long id;
    private Long consumerId;
    private String title;
    private String description;
    private Date creationDate;
    private Date expirationDate;
    private Byte donePercentage;
    private String priority;
    private String status;
    private List<AttachedFileToTaskDto> attachedFileToTaskDtoList = new ArrayList<>();
}
