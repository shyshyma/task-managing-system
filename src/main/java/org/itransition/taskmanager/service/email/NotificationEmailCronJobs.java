package org.itransition.taskmanager.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.constant.CronExpression;
import org.itransition.taskmanager.constant.FreeMarkerTemplatesLocation;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.itransition.taskmanager.service.dto.ConsumerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends email notifications for consumers, which have "notifications_enabled"
 * record to be true
 *
 * @see ConsumerConfig
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEmailCronJobs {

    private final EmailService emailService;
    private final ConsumerService consumerService;

    private static final String SUBJECT_CONTENT = "Task manager: you have a new notification";

    /**
     * Gets all needed data for sending email from the database by notification frequency and transforms it
     * into proper format for email service
     */
    private List<TemplateEmailDetails> getEmailDetailsByNotificationFrequency(NotificationFrequency frequency) {
        List<ConsumerDto> consumers = consumerService.findAllConsumersByEnabledNotificationsAndByFrequency(
                frequency.toString());
        List<TemplateEmailDetails> templateEmailDetails = new ArrayList<>();
        for (ConsumerDto consumerDto : consumers) {
            TemplateEmailDetails emailDetails = new TemplateEmailDetails()
                    .withSubject(SUBJECT_CONTENT)
                    .withTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION)
                    .withDestinationEmail(consumerDto.getEmail())
                    .withTemplateProperty("name", consumerDto.getName())
                    .withTemplateProperty("surname", consumerDto.getSurname());
            templateEmailDetails.add(emailDetails);
        }
        return templateEmailDetails;
    }

    /**
     * Sends emails: for each email couple a new thread
     */
    private void sendNotificationEmails(NotificationFrequency frequency) {
        log.info("Starting to send ");
        List<TemplateEmailDetails> templateEmailDetailsList = getEmailDetailsByNotificationFrequency(
                frequency);
        Runnable runnable = () -> templateEmailDetailsList.forEach(emailService::sendTemplateMessage);
        new Thread(runnable).start();
    }


    @Scheduled(cron = CronExpression.EVERY_DAY)
    public void sendToSubscribersEveryDay() {
        sendNotificationEmails(NotificationFrequency.EVERY_DAY);
    }

    @Scheduled(cron = CronExpression.EVERY_WEEK)
    public void sendToSubscribersEveryWeek() {
        sendNotificationEmails(NotificationFrequency.EVERY_WEEK);
    }

    @Scheduled(cron = CronExpression.EVERY_MONTH)
    public void sendToSubscribersEveryMonth() {
        sendNotificationEmails(NotificationFrequency.EVERY_MONTH);
    }

    @Scheduled(cron = CronExpression.EVERY_YEAR)
    public void sendToSubscribersEveryYear() {
        sendNotificationEmails(NotificationFrequency.EVERY_YEAR);
    }
}
