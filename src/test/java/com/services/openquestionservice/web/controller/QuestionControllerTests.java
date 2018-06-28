package com.services.openquestionservice.web.controller;

import com.services.openquestionservice.OpenQuestionServiceApplication;
import com.services.openquestionservice.questionservice.repository.AnswerRepository;
import com.services.openquestionservice.questionservice.repository.OpenQuestionRepository;
import com.services.openquestionservice.questionservice.service.OpenQuestionService;
import com.services.openquestionservice.web.dto.OpenQuestionDto;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenQuestionServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class QuestionControllerTests {
    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJacksonHttpMessageConverter;

    @Autowired
    private OpenQuestionRepository openQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJacksonHttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("JSON message converter must not be null",
                this.mappingJacksonHttpMessageConverter);
    }

    @Before
    public void setupTests() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test1CreateQuestion() throws Exception {
        // Creating question with all null fields. (SHOULD BE 400 Bad Request)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        null, null, null, null
                )))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        // Creating question with null eventId field. (SHOULD BE 400 Bad Request)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        null,
                        null,
                        "Test question text of createQuestion",
                        "Test voting text of createQuestion")))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        // Creating question with null questionText field. (SHOULD BE 400 Bad Request)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        null,
                        "Test voting text of createQuestion")))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        // Creating question with blank questionText field. (SHOULD BE 400 Bad Request)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        "",
                        "Test voting text of createQuestion")))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        // Creating question with correct questionText field. (SHOULD BE 200 OK)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        "First test question",
                        null)))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType(contentType));

        // Creating question with correct questionText field. (SHOULD BE 203 Created)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        "Second test question",
                        null)))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        // Creating question with correct questionText field. (SHOULD BE 200 Created)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        2L,
                        null,
                        "Third test question",
                        null)))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        assertEquals(openQuestionRepository.count(), 3);
    }

    @Test
    public void test2AddAnswer() {

    }

    private String convertToJson(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJacksonHttpMessageConverter.write(
                object, MediaType.APPLICATION_JSON, mockHttpOutputMessage
        );
        return mockHttpOutputMessage.getBodyAsString();
    }
}
