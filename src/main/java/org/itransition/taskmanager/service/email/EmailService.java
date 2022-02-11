package org.itransition.taskmanager.service.email;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final Configuration configuration;
    private final JavaMailSenderImpl mailSender;
    private final EmailDetailsService emailDetailsService;

    /**
     * Sends emails to all consumers by frequency param, who have enabled notifications
     */
    @Async
    public void sendNotificationToConsumersByFrequency(NotificationFrequency frequency) {
        log.info("Starting to send notification emails for consumers, who have enabled this option");
        List<EmailDetails> emailDetailsList = emailDetailsService
                .findAllEmailDetailsByNotificationFrequency(frequency);
        emailDetailsList.forEach(this::sendTemplateMessage);
    }

    /**
     * Generates HTML email body with the help of freemarker template engine
     * and sends it as MIME type
     */
    @Async
    public void sendTemplateMessage(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(emailDetails.getTemplateLocation()),
                    emailDetails.getTemplateProperties());

            helper.setText(emailContent, true);
            helper.setSubject(emailDetails.getSubject());
            helper.setTo(emailDetails.getDestinationEmail());

            mailSender.send(mimeMessage);
            log.info("Email has been successfully delivered to consumer {}",
                    emailDetails.getDestinationEmail());

        } catch (Exception e) {
            log.error("Email hasn't been delivered to consumer {} due some problems",
                    emailDetails.getDestinationEmail());
        }
    }
}
