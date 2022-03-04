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

    private static final String ENTITY_NAME = "task log";

    public void save(TaskLogMessage taskLogMessage) {
        log.info("Saving '" + ENTITY_NAME + "' entity to mongo persistence unit");
        TaskLog taskLog = taskLogMapper.map(taskLogMessage);
        taskLogRepository.save(taskLog);
    }
}
