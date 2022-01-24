package org.itransition.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "surname", "dateOfBirth", "email"}, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerDto implements Dto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "'name' property must be not null")
    private String name;

    @NotNull(message = "'surname' property must be not null")
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "'dateOfBirth' property must be not null")
    private Date dateOfBirth;

    @Email
    @NotNull(message = "'email' property must be not null")
    private String email;
}
