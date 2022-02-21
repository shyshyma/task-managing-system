package org.itransition.audit.mongo.dao;

import org.itransition.audit.mongo.entity.TaskLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLogRepository extends MongoRepository<TaskLog, String> {
}
