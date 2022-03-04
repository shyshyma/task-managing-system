package org.itransition.audit.service;

import org.itransition.audit.mapper.TaskLogMapper;
import org.itransition.audit.mapper.TaskLogMapperImpl;
import org.itransition.audit.mongo.dao.TaskLogRepository;
import org.itransition.audit.mongo.entity.TaskLog;
import org.itransition.audit.mq.TaskLogMessage;
import org.itransition.audit.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskLogServiceTest {

    @Mock
    private TaskLogRepository taskLogRepository;

    @Spy
    private TaskLogMapper taskLogMapper = new TaskLogMapperImpl();

    @InjectMocks
    private TaskLogService taskLogService;

    @Test
    void testSave() {
        TaskLogMessage message = MessageUtils.generateTaskLogMessage();
        taskLogService.save(message);
        verify(taskLogRepository).save(any(TaskLog.class));
    }
}
