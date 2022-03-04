package org.itransition.audit.service;

import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.itransition.audit.mapper.AttachedFileLogMapper;
import org.itransition.audit.mapper.AttachedFileLogMapperImpl;
import org.itransition.audit.mongo.dao.AttachedFileLogRepository;
import org.itransition.audit.mongo.entity.AttachedFileLog;
import org.itransition.audit.mq.AttachedFileLogMessage;
import org.itransition.audit.utils.MessageUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AttachedFileServiceTest {

    @Mock
    private AttachedFileLogRepository attachedFileLogRepository;

    @Spy
    private AttachedFileLogMapper attachedFileLogMapper = new AttachedFileLogMapperImpl();

    @InjectMocks
    private AttachedFileService attachedFileService;

    @Test
    void save() {
        AttachedFileLogMessage message = MessageUtils.generateAttachedFileLogMessage();
        attachedFileService.save(message);
        verify(attachedFileLogRepository).save(any(AttachedFileLog.class));
    }
}
