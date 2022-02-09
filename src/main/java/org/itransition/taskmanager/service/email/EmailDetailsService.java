package org.itransition.taskmanager.service.email;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.constant.FreeMarkerTemplatesLocation;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.service.ConsumerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailDetailsService {

    private final ConsumerService consumerService;

    /**
     * Gets all needed data for sending email from the database by notification frequency
     * and by enabled notifications
     */
    public List<EmailDetails> findAllEmailDetailsByNotificationFrequency(NotificationFrequency frequency) {
        List<ConsumerDto> consumers = consumerService.findAllConsumersByEnabledNotificationsAndByFrequency(
                frequency.toString());
        List<EmailDetails> templateEmailDetails = new ArrayList<>();
        for (ConsumerDto consumerDto : consumers) {
            EmailDetails emailDetails = new EmailDetails()
                    .withSubject("Task manager: you have a new notification")
                    .withTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION)
                    .withDestinationEmail(consumerDto.getEmail())
                    .withTemplateProperty("name", consumerDto.getName())
                    .withTemplateProperty("surname", consumerDto.getSurname());
            templateEmailDetails.add(emailDetails);
        }
        return templateEmailDetails;
    }
}
