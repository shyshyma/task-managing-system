package org.itransition.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.itransition.taskmanager.jpa.entity.Priority;
import org.itransition.taskmanager.jpa.entity.Status;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiredTaskDto {

    private String title;
    private Date expirationDate;
    private Integer donePercentage;
    private Priority priority;
    private Status status;
}
