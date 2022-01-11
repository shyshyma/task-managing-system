package org.itransition.taskmanager.dtos.jpa;

import lombok.*;
import org.itransition.taskmanager.dtos.Dto;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerDto implements Dto {

    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;
}
