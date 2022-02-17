package org.itransition.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerConfigDto implements Dto {

    private Long id;

    @NotNull(message = "'notifications' property must be not null")
    private Boolean notifications;

    @NotNull(message = "'notificationFrequency' property must be not null")
    private String notificationFrequency;

    @Email
    @NotNull(message = "'email' property must be not null")
    private String email;
}
