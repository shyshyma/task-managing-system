package org.itransition.taskmanager.config;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.service.email.EmailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.EnumSet;

@Configuration
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer {

    private final EmailService emailService;

    /**
     * Parses all CRONs from frequency enum and creates
     * email notification scheduled tasks
     */
    private void addEmailNotificationCronTasks(ScheduledTaskRegistrar taskRegistrar) {
        EnumSet<NotificationFrequency> frequencies = EnumSet.allOf(NotificationFrequency.class);
        for (NotificationFrequency frequency : frequencies) {
            taskRegistrar.addCronTask(() -> emailService.sendNotificationToConsumersByFrequency(frequency),
                    frequency.getCronExpression());
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        addEmailNotificationCronTasks(taskRegistrar);
    }
}
