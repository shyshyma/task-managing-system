package org.itransition.taskmanager.jpa.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers-config.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-tasks.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-tasks.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-consumers.sql")
class TaskRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findTaskTitleById() {
        String taskTitleById = taskRepository.findTaskTitleById(1L);
        assertEquals("repair car", taskTitleById);
    }
}
