package org.itransition.taskmanager.controller.rest;

import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.utils.DtoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers-config.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-consumers.sql")
public class ConsumerConfigControllerTest extends BaseControllerTest {

    private static final Long CONSUMER_CONFIG_ID_THAT_IN_DATABASE = 1L;
    private static final Long CONSUMER_CONFIG_ID_THAT_NOT_IN_DATABASE = 101L;

    @Test
    void getConsumerConfigReturns200AndConsumerConfigJson() throws Exception {
        ResultActions resultActions = getMockMvc().perform(get("/api/consumers/{id}/config",
                        CONSUMER_CONFIG_ID_THAT_IN_DATABASE).contentType(getJsonContentType()))
                .andExpect(status().isOk())
                .andExpect(this::isJsonContentType);
        expectJsonObject(resultActions);
    }

    @Test
    void getConsumerConfigReturns404AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(get("/api/consumers/{id}/config",
                        CONSUMER_CONFIG_ID_THAT_NOT_IN_DATABASE).contentType(getJsonContentType()))
                .andExpect(status().isNotFound())
                .andExpect(this::isModelNotFoundException)
                .andExpect(this::isJsonContentType);
    }

    @Test
    void updateConsumerConfigReturns200AndConsumerConfigJson() throws Exception {
        ConsumerConfigDto configDto = DtoUtils.generateConsumerConfigDto();
        ResultActions resultActions = getMockMvc().perform(put("/api/consumers/{id}/config",
                        CONSUMER_CONFIG_ID_THAT_IN_DATABASE).contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(configDto)))
                .andExpect(status().isOk())
                .andExpect(this::isJsonContentType);
        expectJsonObject(resultActions);
    }

    @Test
    void updateConsumerConfigReturns404AndThrowsModelNotFoundException() throws Exception {
        ConsumerConfigDto configDto = DtoUtils.generateConsumerConfigDto();
        getMockMvc().perform(put("/api/consumers/{id}/config",
                        CONSUMER_CONFIG_ID_THAT_NOT_IN_DATABASE).contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(configDto)))
                .andExpect(status().isNotFound())
                .andExpect(this::isModelNotFoundException)
                .andExpect(this::isJsonContentType);
    }

    @Override
    protected ResultActions expectJsonObject(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.notifications").isNotEmpty())
                .andExpect(jsonPath("$.notificationFrequency").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty());
    }
}
