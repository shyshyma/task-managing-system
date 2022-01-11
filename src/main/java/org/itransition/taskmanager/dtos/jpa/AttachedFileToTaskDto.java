package org.itransition.taskmanager.dtos.jpa;

import lombok.*;
import org.itransition.taskmanager.dtos.Dto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachedFileToTaskDto implements Dto {

    private byte[] data;
    private String name;
}
