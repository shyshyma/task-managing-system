package org.itransition.taskmanager.event;

import lombok.Getter;
import org.itransition.taskmanager.service.email.EmailDetails;
import org.springframework.context.ApplicationEvent;

@Getter
public class SuccessRegistrationEvent extends ApplicationEvent {

    private final EmailDetails emailDetails;

    public SuccessRegistrationEvent(Object source, EmailDetails emailDetails) {
        super(source);
        this.emailDetails = emailDetails;
    }
}
