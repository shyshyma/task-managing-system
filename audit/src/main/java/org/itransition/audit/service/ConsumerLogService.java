package org.itransition.audit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.audit.mapper.ConsumerLogMapper;
import org.itransition.audit.mongo.dao.ConsumerLogRepository;
import org.itransition.audit.mongo.entity.ConsumerLog;
import org.itransition.audit.mq.ConsumerLogMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerLogService {

    private final ConsumerLogMapper consumerLogMapper;
    private final ConsumerLogRepository consumerLogRepository;

    private static final String ENTITY_NAME = "consumer log";

    public void save(ConsumerLogMessage consumerLogMessage) {
        log.info("Saving '" + ENTITY_NAME + "' entity to mongo persistence unit");
        ConsumerLog consumerLog = consumerLogMapper.map(consumerLogMessage);
        consumerLogRepository.save(consumerLog);
    }
}
