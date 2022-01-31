package org.itransition.taskmanager.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
public abstract class BaseControllerTest {

    private final String pageSizeParam = "size";
    private final String pageNumberParam = "page";

    private final String jsonArrayPattern = "$.*";

    private  final String jsonContentType = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected void isModelNotFoundException(MvcResult result) {
        assertEquals(requireNonNull(result.getResolvedException()).getClass(),
                ModelNotFoundException.class);
    }

    protected void isJsonContentType(MvcResult result) {
        assertEquals(requireNonNull(result.getResponse().getContentType()), jsonContentType);
    }

    protected abstract ResultActions expectJsonObject(ResultActions resultActions) throws Exception;
}
