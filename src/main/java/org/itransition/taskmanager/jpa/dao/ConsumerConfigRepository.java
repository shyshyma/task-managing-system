package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerConfigRepository extends BaseRepository<ConsumerConfig> {

    boolean existsByEmail(String email);

    @Query("SELECT config.email FROM ConsumerConfig config WHERE config.id = ?1")
    String findEmailById(Long id);

    @Modifying
    @Query("UPDATE ConsumerConfig config "
            + "SET config.email = ?1 "
            + "WHERE config.id = ?2")
    void updateEmailById(String email, Long id);
}
