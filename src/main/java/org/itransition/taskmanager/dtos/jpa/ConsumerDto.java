package org.itransition.taskmanager.dtos.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.itransition.taskmanager.dtos.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerDto implements Dto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "'name' property must be not null")
    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    @NotNull(message = "'surname' property must be not null")
    @JsonProperty(value = "surname", access = JsonProperty.Access.READ_WRITE)
    private String surname;

    @NotNull(message = "'birthDate' property must be not null")
    @JsonProperty(value = "birthDate", access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Email
    @NotNull(message = "'email' property must be not null")
    @JsonProperty(value = "email", access = JsonProperty.Access.READ_WRITE)
    private String email;
}
