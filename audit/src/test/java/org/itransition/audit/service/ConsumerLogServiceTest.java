package org.itransition.audit.service;

import org.mockito.Mock;
import org.mockito.Spy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.itransition.audit.mapper.ConsumerLogMapper;
import org.itransition.audit.mapper.ConsumerLogMapperImpl;
import org.itransition.audit.mongo.dao.ConsumerLogRepository;
import org.itransition.audit.mongo.entity.ConsumerLog;
import org.itransition.audit.mq.ConsumerLogMessage;
import org.itransition.audit.utils.MessageUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumerLogServiceTest {

    @Mock
    private ConsumerLogRepository consumerLogRepository;

    @Spy
    private ConsumerLogMapper consumerLogMapper = new ConsumerLogMapperImpl();

    @InjectMocks
    private ConsumerLogService consumerLogService;

    @Test
    void save() {
        ConsumerLogMessage message = MessageUtils.generateConsumerLogMessage();
        consumerLogService.save(message);
        verify(consumerLogRepository).save(any(ConsumerLog.class));
    }
}
