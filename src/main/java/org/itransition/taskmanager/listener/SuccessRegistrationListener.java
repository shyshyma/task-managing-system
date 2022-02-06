package org.itransition.taskmanager.listener;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.event.SuccessRegistrationEvent;
import org.itransition.taskmanager.service.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuccessRegistrationListener {

    private final EmailService emailService;

    @EventListener
    public void handleSuccessRegistrationEvent(SuccessRegistrationEvent event) {
        Runnable runnable = () ->emailService.sendTemplateMessage(event.getTemplateEmailDetails());
        new Thread(runnable).start();
    }
}
