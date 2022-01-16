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
public class ConsumerDto implements Dto {

    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonProperty(value = "surname", access = JsonProperty.Access.READ_WRITE)
    private String surname;

    @JsonProperty(value = "birthDate", access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @JsonProperty(value = "email", access = JsonProperty.Access.READ_WRITE)
    private String email;
}
