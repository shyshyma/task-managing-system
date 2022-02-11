package org.itransition.taskmanager.jpa.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers-config.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-consumers.sql")
class ConsumerConfigRepositoryTest extends BaseRepositoryTest {

    private static final Long CONSUMER_ID_THAT_IN_DATABASE = 1L;

    @Autowired
    private ConsumerConfigRepository consumerConfigRepository;

    @Test
    void findEmailById() {
        String emailById = consumerConfigRepository.findEmailById(CONSUMER_ID_THAT_IN_DATABASE);
        assertEquals("ashyshyma@gmail.com", emailById);
    }

    @Test
    void updateEmailById() {
        String email = "test-update@gmail.com";
        consumerConfigRepository.updateEmailById(email, CONSUMER_ID_THAT_IN_DATABASE);
        //optional always not be null, because it definitely presents in persistance layer
        String emailFromRepo = consumerConfigRepository.findById(1L).get().getEmail();
        assertEquals(email, emailFromRepo);
    }
}
