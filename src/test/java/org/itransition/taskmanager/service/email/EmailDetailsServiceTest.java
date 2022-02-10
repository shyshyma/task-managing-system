package org.itransition.taskmanager.service.email;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.mapper.EmailDetailsMapper;
import org.itransition.taskmanager.mapper.EmailDetailsMapperImpl;
import org.itransition.taskmanager.service.ConsumerService;
import org.itransition.taskmanager.utils.DtoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailDetailsServiceTest {

    @Mock
    private ConsumerService consumerService;

    @Spy
    private EmailDetailsMapper emailDetailsMapper = new EmailDetailsMapperImpl();

    @InjectMocks
    private EmailDetailsService emailDetailsService;

    @Test
    void testFindAllEmailDetailsByNotificationFrequency() {
        List<ConsumerDto> consumerDtos = List.of(DtoUtils.generateConsumerDto(),
                DtoUtils.generateConsumerDto(), DtoUtils.generateConsumerDto(),
                DtoUtils.generateConsumerDto(), DtoUtils.generateConsumerDto());

        NotificationFrequency frequency = NotificationFrequency.EVERY_DAY;

        when(consumerService.findAllConsumersByEnabledNotificationsAndByFrequency(frequency))
                .thenReturn(consumerDtos);

        List<EmailDetails> details = emailDetailsService
                .findAllEmailDetailsByNotificationFrequency(frequency);

        assertEquals(5, details.size());

        verify(consumerService)
                .findAllConsumersByEnabledNotificationsAndByFrequency(frequency);
    }
}
