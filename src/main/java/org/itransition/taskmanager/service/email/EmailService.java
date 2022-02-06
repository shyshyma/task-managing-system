package org.itransition.taskmanager.service.email;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final Configuration configuration;
    private final JavaMailSenderImpl mailSender;

    private static final String EMAIL_SUCCESSFUL_DELIVERY_MESSAGE = "Email has been successfully"
            + " delivered to consumer {}";
    private static final String EMAIL_UNSUCCESSFUL_DELIVERY_MESSAGE = "Email hasn't been delivered to" +
            " consumer {} due some problems";

    @Value("${spring.mail.username}")
    private String replyToEmailAddress;

    /**
     * Generates HTML email body with the help of freemarker template engine
     * and sends it as MIME type
     */
    public void sendTemplateMessage(TemplateEmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(emailDetails.getTemplateLocation()),
                    emailDetails.getTemplateProperties());

            helper.setText(emailContent, true);
            helper.setSubject(emailDetails.getSubject());
            helper.setTo(emailDetails.getDestinationEmail());
            helper.setReplyTo(replyToEmailAddress);

            mailSender.send(mimeMessage);
            log.info(EMAIL_SUCCESSFUL_DELIVERY_MESSAGE, emailDetails.getDestinationEmail());

        } catch (Exception e) {
            log.error(EMAIL_UNSUCCESSFUL_DELIVERY_MESSAGE, emailDetails.getDestinationEmail());
        }
    }

    /**
     * Sends email letter with simple text body
     */
    public void sendSimpleMessage(SimpleEmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(emailDetails.getDestinationEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getText());
        simpleMailMessage.setReplyTo(replyToEmailAddress);

        mailSender.send(simpleMailMessage);
        log.info(EMAIL_SUCCESSFUL_DELIVERY_MESSAGE, emailDetails.getDestinationEmail());
    }
}
