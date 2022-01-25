package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.Consumer;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends BaseRepository<Consumer> {

    boolean existsByEmail(String email);
}
