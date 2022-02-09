package org.itransition.taskmanager.listener;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.event.SuccessRegistrationEvent;
import org.itransition.taskmanager.service.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuccessRegistrationListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void handleSuccessRegistrationEvent(SuccessRegistrationEvent event) {
        emailService.sendTemplateMessage(event.getEmailDetails());
    }
}
