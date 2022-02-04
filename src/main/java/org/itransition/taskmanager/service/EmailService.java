package org.itransition.taskmanager.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.dto.SuccessRegistrationEmailDto;
import org.itransition.taskmanager.dto.ExpiredTasksEmailDto;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String TEMPLATE_HEADER_KEY = "header";

    private static final String SUCCESS_REGISTRATION_FTL_LOCATION = "mail/success-registration.ftl";
    private static final String EXPIRED_TASKS_FTL_LOCATION = "mail/expired-tasks.ftl";

    private static final String EXPIRED_TASKS_NOTIFICATION_SUBJECT = "Task Manager: expired tasks";
    private static final String SUCCESS_REGISTRATION_SUBJECT = "Task Manager: thank you for registration"
            + " in our resource";

    private final Configuration configuration;
    private final JavaMailSenderImpl mailSender;

    public void sendSuccessRegistrationNotification(SuccessRegistrationEmailDto successRegistrationEmailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            Map<String, Object> templateData = new HashMap<>();
            templateData.put(TEMPLATE_HEADER_KEY, "Thanks for registration");
            templateData.put("name", successRegistrationEmailDto.getName());
            templateData.put("surname", successRegistrationEmailDto.getSurname());

            String emailContent = generateHtmlText(SUCCESS_REGISTRATION_FTL_LOCATION, templateData);
            helper.setText(emailContent, true);

            helper.setSubject(SUCCESS_REGISTRATION_SUBJECT);
            helper.setTo(successRegistrationEmailDto.getEmail());

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error("Success registration email hasn't been"
                    + " delivered to consumer {}", successRegistrationEmailDto.getEmail());
        }
    }

    public void sendExpiredTasksNotification(ExpiredTasksEmailDto expiredTasksEmailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            Map<String, Object> templateData = new HashMap<>();
            templateData.put(TEMPLATE_HEADER_KEY, "Expired tasks");
            templateData.put("name", expiredTasksEmailDto.getName());
            templateData.put("surname", expiredTasksEmailDto.getSurname());
            templateData.put("excludedTasks", expiredTasksEmailDto.getExpiredTaskDtoList());

            String emailContent = generateHtmlText(EXPIRED_TASKS_FTL_LOCATION, templateData);
            helper.setText(emailContent, true);

            helper.setSubject(EXPIRED_TASKS_NOTIFICATION_SUBJECT);
            helper.setTo(expiredTasksEmailDto.getEmail());

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error("Expired tasks notification email hasn't been"
                    + " delivered to consumer {}", expiredTasksEmailDto.getEmail());
        }
    }

    private String generateHtmlText(String templateLocation, Map<String, Object> model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(
                configuration.getTemplate(templateLocation),
                model);
    }
}
