package org.itransition.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuccessRegistrationEmailDto implements Dto {

    private String name;
    private String surname;
    private String email;
}
