package org.itransition.audit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.audit.mapper.AttachedFileLogMapper;
import org.itransition.audit.mongo.dao.AttachedFileLogRepository;
import org.itransition.audit.mongo.entity.AttachedFileLog;
import org.itransition.audit.mq.AttachedFileLogMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachedFileService {

    private AttachedFileLogMapper attachedFileLogMapper;
    private AttachedFileLogRepository attachedFileLogRepository;

    private static final String ENTITY_NAME = "attached file log";

    public void save(AttachedFileLogMessage attachedFileLogMessage) {
        log.info("Saving '" + ENTITY_NAME + "' entity to mongo persistence unit");
        AttachedFileLog attachedFileLog = attachedFileLogMapper.map(attachedFileLogMessage);
        attachedFileLogRepository.save(attachedFileLog);
    }
}
