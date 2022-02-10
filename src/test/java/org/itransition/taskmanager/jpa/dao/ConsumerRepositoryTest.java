package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers-config.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-consumers.sql")
class ConsumerRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Test
    void findByEnabledNotificationsAndByFrequency() {
        List<Consumer> byFrequency = consumerRepository
                .findByEnabledNotificationsAndByFrequency(NotificationFrequency.EVERY_DAY);
        assertEquals(1, byFrequency.size());
    }
}
