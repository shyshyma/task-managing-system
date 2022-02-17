package org.itransition.taskmanager.controller.rest;

import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.exception.DuplicateTitleException;
import org.itransition.taskmanager.utils.DtoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.empty;
import static java.util.Objects.requireNonNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/sql/populate-consumers.sql",
                "/sql/populate-consumers-config.sql",
                "/sql/populate-tasks.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = {"/sql/drop-consumers.sql",
                "/sql/drop-tasks.sql"})
class TaskControllerTest extends BaseControllerTest {

    private static final Integer CONSUMER_ID_THAT_IN_DATABASE = 1;
    private static final Integer TASK_ID_THAT_IN_DATABASE = 1;
    private static final Integer CONSUMER_ID_THAT_NOT_IN_DATABASE = 567;
    private static final Integer TASK_ID_THAT_NOT_IN_DATABASE = 345;
    private static final String TASK_TITLE_THAT_IN_DATABASE = "programming";

    @Test
    void getConsumerTaskReturns404AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void getConsumerTaskReturns200AndTaskJson() throws Exception {
        ResultActions resultActions = getMockMvc()
                .perform(get("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(status().isOk());
        expectJsonObject(resultActions);
    }

    @Test
    void getConsumerTasksReturns200AndDefaultPageOfTaskJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(3)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isOk());
    }

    @Test
    void getConsumerTasksReturns200AndPageWithSizeOf2TaskJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .param(getPageNumberParam(), "0")
                        .param(getPageSizeParam(), "2"))
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(2)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isOk());
    }

    @Test
    void getConsumerTasksReturns200AndEmptyPageOfTasksJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(this::isJsonContentType)
                .andExpect(jsonPath(getJsonArrayPattern(), empty()))
                .andExpect(status().isOk());
    }

    @Test
    void saveConsumerTaskReturns201AndTaskJson() throws Exception {
        TaskDto taskDto = DtoUtils.generateTaskDto();
        ResultActions resultActions = getMockMvc()
                .perform(post("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(taskDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isCreated());
        expectJsonObject(resultActions);
    }

    @Test
    void saveConsumerTaskReturns400AndThrowsDuplicateTitleException() throws Exception {
        TaskDto taskDto = DtoUtils.generateTaskDto();
        taskDto.setTitle(TASK_TITLE_THAT_IN_DATABASE);
        getMockMvc().perform(post("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(taskDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(result ->
                        assertEquals(requireNonNull(result.getResolvedException()).getClass(),
                                DuplicateTitleException.class))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveConsumerTaskReturns404AndThrowsModelNotFoundException() throws Exception {
        TaskDto taskDto = DtoUtils.generateTaskDto();
        getMockMvc().perform(post("/api/consumers/{consumerId}/tasks",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isNotFound())
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException);
    }

    @Test
    void updateConsumerTaskReturns200AndTaskJson() throws Exception {
        TaskDto taskDto = DtoUtils.generateTaskDto();
        ResultActions resultActions = getMockMvc()
                .perform(put("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(taskDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isOk());
        expectJsonObject(resultActions);
    }

    @Test
    void updateConsumerTaskReturns400AndThrowsModelNotFoundException() throws Exception {
        TaskDto taskDto = DtoUtils.generateTaskDto();
        getMockMvc().perform(put("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(taskDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteConsumerTaskReturns204() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteConsumerTaskReturns400AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}/tasks/{taskId}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Override
    protected ResultActions expectJsonObject(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.description").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty())
                .andExpect(jsonPath("$.expirationDate").isNotEmpty())
                .andExpect(jsonPath("$.donePercentage").isNotEmpty())
                .andExpect(jsonPath("$.priority").isNotEmpty())
                .andExpect(jsonPath("$.status").isNotEmpty());
    }
}
