package org.itransition.taskmanager.service.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Provides core sending email letter information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EmailDetails {

    protected String subject;
    protected String destinationEmail;
}
