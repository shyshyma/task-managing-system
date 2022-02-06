package org.itransition.taskmanager.service.email;

import lombok.Getter;

/**
 * For sending email message with text body
 *
 * @see EmailDetails
 */
@Getter
public class SimpleEmailDetails extends EmailDetails {

    private String text;

    public SimpleEmailDetails withText(String text) {
        this.text = text;
        return this;
    }

    public SimpleEmailDetails withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public SimpleEmailDetails withDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
        return this;
    }
}
