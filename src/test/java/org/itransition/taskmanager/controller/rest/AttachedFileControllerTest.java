package org.itransition.taskmanager.controller.rest;

import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.exception.DuplicateFileNameException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/sql/populate-consumers.sql",
                "/sql/populate-tasks.sql",
                "/sql/populate-attached-files.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = {"/sql/drop-consumers.sql",
                "/sql/drop-tasks.sql",
                "/sql/drop-attached-files.sql"})
class AttachedFileControllerTest extends BaseControllerTest {

    private static final Integer CONSUMER_ID_THAT_IN_DATABASE = 1;
    private static final Integer TASK_ID_THAT_IN_DATABASE = 1;
    private static final String ATTACHED_FILE_NAME_THAT_IN_DATABASE = "text.txt";

    private static final Integer CONSUMER_ID_THAT_NOT_IN_DATABASE = 567;
    private static final Integer TASK_ID_THAT_NOT_IN_DATABASE = 345;
    private static final String ATTACHED_FILE_NAME_THAT_NOT_IN_DATABASE = "file-name-not-in-db.txt";

    @Test
    void getConsumerTaskAttachedFileReturns200AndAttachedFileJson() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks/"
                                + "{taskId}/files/{attachedFileName}", CONSUMER_ID_THAT_IN_DATABASE,
                        TASK_ID_THAT_IN_DATABASE, ATTACHED_FILE_NAME_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    void getConsumerTaskAttachedFileReturns404AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks/"
                                + "{taskId}/files/{attachedFileName}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE,
                        ATTACHED_FILE_NAME_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void getConsumerTaskAttachedFilesReturns200AndDefaultPageOfFilesJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(3)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isOk());
    }

    @Test
    void getConsumerTaskAttachedFilesReturns200AndPageWithSizeOf2FilesJsons() throws Exception {
        getMockMvc().perform(get("/api/consumers/{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .param(getPageNumberParam(), "0")
                        .param(getPageSizeParam(), "2")
                        .contentType(getJsonContentType()))
                .andExpect(jsonPath(getJsonArrayPattern(), hasSize(2)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isOk());
    }

    @Test
    void saveConsumerTaskAttachedFileReturns201AndAttachedFileJson() throws Exception {
        AttachedFileDto fileDto = DtoUtils.generateAttachedFileDto();
        ResultActions resultActions = getMockMvc().perform(post("/api/consumers/"
                                + "{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(fileDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(status().isCreated());
        expectJsonObject(resultActions);
    }

    @Test
    void saveConsumerTaskAttachedFileReturns400AndDuplicateFileNameException() throws Exception {
        AttachedFileDto fileDto = DtoUtils.generateAttachedFileDto();
        fileDto.setName(ATTACHED_FILE_NAME_THAT_IN_DATABASE);
        getMockMvc().perform(post("/api/consumers/{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(fileDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(result ->
                        assertEquals(requireNonNull(result.getResolvedException()).getClass(),
                                DuplicateFileNameException.class))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveConsumerTaskAttachedFileReturns404AndModelNotFoundException() throws Exception {
        AttachedFileDto fileDto = DtoUtils.generateAttachedFileDto();
        getMockMvc().perform(post("/api/consumers/{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(fileDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateConsumerTaskAttachedFileReturns200AndFileMetadataJson() throws Exception {
        AttachedFileDto fileDto = DtoUtils.generateAttachedFileDto();
        fileDto.setName(ATTACHED_FILE_NAME_THAT_IN_DATABASE);
        ResultActions resultActions =
                getMockMvc().perform(put("/api/consumers/{consumerId}/tasks/{taskId}/files",
                                CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE)
                                .contentType(getJsonContentType())
                                .content(getObjectMapper().writeValueAsString(fileDto)))
                        .andExpect(this::isJsonContentType)
                        .andExpect(status().isOk());
        expectJsonObject(resultActions);
    }

    @Test
    void updateConsumerTaskAttachedFileReturns404AndModelNotFoundException() throws Exception {
        AttachedFileDto fileDto = DtoUtils.generateAttachedFileDto();
        fileDto.setName(ATTACHED_FILE_NAME_THAT_IN_DATABASE);
        getMockMvc().perform(put("/api/consumers/{consumerId}/tasks/{taskId}/files",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType())
                        .content(getObjectMapper().writeValueAsString(fileDto)))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteConsumerTaskAttachedFileReturns204() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}/tasks/{taskId}/files/{fileName}",
                        CONSUMER_ID_THAT_IN_DATABASE, TASK_ID_THAT_IN_DATABASE,
                        ATTACHED_FILE_NAME_THAT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteConsumerTaskAttachedFileReturns404AndThrowsModelNotFoundException() throws Exception {
        getMockMvc().perform(delete("/api/consumers/{consumerId}/tasks/{taskId}/files/{fileName}",
                        CONSUMER_ID_THAT_NOT_IN_DATABASE, TASK_ID_THAT_NOT_IN_DATABASE,
                        ATTACHED_FILE_NAME_THAT_NOT_IN_DATABASE)
                        .contentType(getJsonContentType()))
                .andExpect(this::isJsonContentType)
                .andExpect(this::isModelNotFoundException)
                .andExpect(status().isNotFound());
    }

    @Override
    protected ResultActions expectJsonObject(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.fileName").isNotEmpty())
                .andExpect(jsonPath("$.size").isNotEmpty())
                .andExpect(jsonPath("$.downloadPath").isNotEmpty());
    }
}
