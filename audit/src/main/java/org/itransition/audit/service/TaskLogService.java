package org.itransition.audit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.audit.mapper.TaskLogMapper;
import org.itransition.audit.mongo.dao.TaskLogRepository;
import org.itransition.audit.mongo.entity.TaskLog;
import org.itransition.audit.mq.TaskLogMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskLogService {

    private final TaskLogMapper taskLogMapper;
    private final TaskLogRepository taskLogRepository;

    public void save(TaskLogMessage taskLogMessage) {
        TaskLog taskLog = taskLogMapper.map(taskLogMessage);
        log.info("Saving 'task-log' entity to mongo persistence unit");
        taskLogRepository.save(taskLog);
    }
}
