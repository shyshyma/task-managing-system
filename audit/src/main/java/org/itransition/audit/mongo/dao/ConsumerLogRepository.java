package org.itransition.audit.mongo.dao;

import org.itransition.audit.mongo.entity.ConsumerLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerLogRepository extends MongoRepository<ConsumerLog, String> {
}
