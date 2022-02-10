package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.Consumer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends BaseRepository<Consumer> {

    @Query("SELECT c FROM Consumer c "
            + "INNER JOIN ConsumerConfig config ON c.id = config.id "
            + "WHERE config.notifications = true and config.notificationFrequency = ?1")
    List<Consumer> findByEnabledNotificationsAndByFrequency(String notificationFrequency);
}
