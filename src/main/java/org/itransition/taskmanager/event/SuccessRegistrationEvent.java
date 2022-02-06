package org.itransition.taskmanager.event;

import lombok.Getter;
import org.itransition.taskmanager.service.email.TemplateEmailDetails;
import org.springframework.context.ApplicationEvent;

@Getter
public class SuccessRegistrationEvent extends ApplicationEvent {

    private final TemplateEmailDetails templateEmailDetails;

    public SuccessRegistrationEvent(Object source, TemplateEmailDetails emailDetails) {
        super(source);
        this.templateEmailDetails = emailDetails;
    }
}
