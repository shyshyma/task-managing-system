package org.itransition.taskmanager.controller.rest;

import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.utils.DtoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/sql/populate-consumers-config.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "/sql/drop-consumers.sql")
class ConsumerControllerTest extends BaseControllerTest {

    private static final Integer CONSUMER_ID_THAT_IN_DATABASE = 1;
    private static final String CONSUMER_EMAIL_THAT_IN_DATABASE = "ashyshyma@gmail.com";
    private static final Integer CONSUMER_ID_THAT_NOT_IN_DATABASE = 101;
    private static final Integer CONSUMERS_COUNT = 6;

    @Test
    void getConsumerReturns404AndModelNotFoundExceptionThrown() throws Exception {
        getMockMvc().perform(get("/api/consumers/{id}", CONSUMER_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void getConsumerReturns200AndConsumerJson() throws Exception {
        ResultActions resultActions =
                getMockMvc().perform(get("/api/consumers/{id}", CONSUMER_ID_THAT_IN_DATABASE)
                                .contentType(getJsonContentType()))
                        .andExpect(this::isJsonContentType)
                        .andExpect(status().isOk());
        expectJsonObject(resultActions);
    }

    @Test
    void getConsumersReturns200AndDefaultPageOfConsumersJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers")
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(CONSUMERS_COUNT)))
                .andExpect(status().isOk());
    }

    @Test
    void getConsumersReturns200And1PageWithSizeOf3ConsumerJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers")
                        .contentType(getJsonContentType())
                        .param(getPageNumberParam(), "1")
                        .param(getPageSizeParam(), "3"))
                .andExpect(this::isJsonContentType)
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    void saveConsumerReturns201AndConsumerJson() throws Exception {
        ConsumerDto consumerDto = DtoUtils.generateConsumerDto();
        ResultActions resultActions = getMockMvc().perform(post("/api/consumers")
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(consumerDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isCreated());
        expectJsonObject(resultActions);
    }

    @Test
    void saveConsumerReturns400AndThrowsDuplicateEmailException() throws Exception {
        ConsumerDto consumerDto = DtoUtils.generateConsumerDto();
        consumerDto.setEmail(CONSUMER_EMAIL_THAT_IN_DATABASE);
        getMockMvc().perform(post("/api/consumers")
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(consumerDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(result ->
                        assertEquals(requireNonNull(result.getResolvedException()).getClass(),
                                DuplicateEmailException.class))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateConsumerReturns200AndConsumerJson() throws Exception {
        ConsumerDto consumerDto = DtoUtils.generateConsumerDto();
        ResultActions resultActions =
                getMockMvc().perform(put("/api/consumers/{consumerId}",
                                CONSUMER_ID_THAT_IN_DATABASE)
                                .contentType(getJsonContentType())
                                .content(getObjectMapper().writeValueAsString(consumerDto)))
                        .andExpect(this::isJsonContentType)
                        .andExpect(status().isOk());
        expectJsonObject(resultActions);
    }

    @Test
    void updateConsumerReturns404AndThrowsModelNotFoundException() throws Exception {
        ConsumerDto consumerDto = DtoUtils.generateConsumerDto();
        getMockMvc().perform(put("/api/consumers/{consumerId}", CONSUMER_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(consumerDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteConsumerReturns204() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}", CONSUMER_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteConsumerReturns404AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Override
    protected ResultActions expectJsonObject(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.surname").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.dateOfBirth").isNotEmpty());
    }
}
