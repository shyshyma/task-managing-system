package org.itransition.taskmanager.service.email;

import freemarker.template.Configuration;
import org.itransition.taskmanager.constant.FreeMarkerTemplatesLocation;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.utils.EmailUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSenderImpl mailSender;

    @Mock
    private EmailDetailsService emailDetailsService;

    @Spy
    private static Configuration configuration;

    @InjectMocks
    private EmailService emailService;

    private static final MimeMessage MIME_MESSAGE = new MimeMessage(Session.getInstance(new Properties()));
    private static final String FREEMARKER_TEMPLATES_FOLDER = "src/main/resources/templates";

    @BeforeAll
    static void configureFreemarker() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setDirectoryForTemplateLoading(new File(FREEMARKER_TEMPLATES_FOLDER));
    }

    @Test
    void testSendNotificationToConsumersByFrequency() {
        EmailDetails firstEmailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION);

        EmailDetails secondEmailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION);

        EmailDetails thirdEmailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION);

        EmailDetails fourthEmailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION);

        when(emailDetailsService
                .findAllEmailDetailsByNotificationFrequency(NotificationFrequency.EVERY_DAY))
                .thenReturn(List.of(firstEmailDetails, secondEmailDetails, thirdEmailDetails, fourthEmailDetails));

        when(mailSender.createMimeMessage()).thenReturn(MIME_MESSAGE);

        emailService.sendNotificationToConsumersByFrequency(NotificationFrequency.EVERY_DAY);

        verify(emailDetailsService).findAllEmailDetailsByNotificationFrequency(NotificationFrequency.EVERY_DAY);
        verify(mailSender, times(4)).createMimeMessage();
        verify(mailSender, times(4)).send(MIME_MESSAGE);

        verifyNoMoreInteractions(mailSender);
        verifyNoMoreInteractions(emailDetailsService);
    }

    @Test
    void testSendTemplateMessageNotification() {
        when(mailSender.createMimeMessage()).thenReturn(MIME_MESSAGE);

        EmailDetails emailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.NOTIFICATION);
        emailService.sendTemplateMessage(emailDetails);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(MIME_MESSAGE);
        verifyNoMoreInteractions(mailSender);
    }

    @Test
    void testSendTemplateMessageSuccessRegistration() {
        when(mailSender.createMimeMessage()).thenReturn(MIME_MESSAGE);

        EmailDetails emailDetails = EmailUtils
                .generateEmailDetailsByTemplateLocation(FreeMarkerTemplatesLocation.SUCCESS_REGISTRATION);
        emailService.sendTemplateMessage(emailDetails);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(MIME_MESSAGE);
        verifyNoMoreInteractions(mailSender);
    }
}
