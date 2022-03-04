package org.itransition.audit.mongo.dao;

import org.itransition.audit.mongo.entity.AttachedFileLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachedFileLogRepository extends MongoRepository<AttachedFileLog, String> {
}
