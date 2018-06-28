package com.services.openquestionservice.web.controller;

import com.services.openquestionservice.OpenQuestionServiceApplication;
import com.services.openquestionservice.questionservice.Constants;
import com.services.openquestionservice.questionservice.repository.AnswerRepository;
import com.services.openquestionservice.questionservice.repository.OpenQuestionRepository;
import com.services.openquestionservice.web.dto.AnswerDto;
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

        // Creating question with correct questionText field. (SHOULD BE 203 Created)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        "First test question",
                        null)))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.votingText", is(Constants.DEFAULT_VOTING_TEXT.toString())))
            .andExpect(jsonPath("$.answers", is(nullValue())));

        // Creating question with correct questionText field. (SHOULD BE 203 Created)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        1L,
                        null,
                        "Second test question",
                        "Voting text 2")))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.votingText", is("Voting text 2")))
                .andExpect(jsonPath("$.answers", is(nullValue())));

        // Creating question with correct questionText field. (SHOULD BE 200 Created)
        mockMvc.perform(post("/api/open_question/")
                .content(convertToJson(new OpenQuestionDto(
                        2L,
                        null,
                        "Third test question",
                        "Voting text 3")))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.votingText", is("Voting text 3")))
                .andExpect(jsonPath("$.answers", is(nullValue())));
    }

    @Test
    public void test2AddAnswer() throws Exception {
        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(null, null, null)))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(1L, null, null)))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(null, 1L, null)))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(1L, 1L, "")))
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(1L, 1L, "Fist answer")))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(1)));

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(1L, 3L, "Second answer")))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(2)));

        mockMvc.perform(post("/api/open_question/answer")
                .content(convertToJson(new AnswerDto(2L, 1L, "Third answer")))
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    public void test3ChangeQuestionText() throws Exception {
        mockMvc.perform(put("/api/open_question/1/question_text")
                .content("")
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(put("/api/open_question/1/question_text")
                .content("Changed q1 text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/api/open_question/2/question_text")
                .content("Changed q2 text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/api/open_question/3/question_text")
                .content("Changed q3 text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test4ChangeVotingText() throws Exception {
        mockMvc.perform(put("/api/open_question/1/voting_text")
                .content("")
                .contentType(contentType)
        ).andExpect(status().isBadRequest());

        mockMvc.perform(put("/api/open_question/1/voting_text")
                .content("Changed q1 voting text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/api/open_question/2/voting_text")
                .content("Changed q2 voting text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/api/open_question/3/voting_text")
                .content("Changed q3 voting text")
                .contentType(contentType)
        ).andExpect(status().is2xxSuccessful());
    }

    private String convertToJson(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJacksonHttpMessageConverter.write(
                object, MediaType.APPLICATION_JSON, mockHttpOutputMessage
        );
        return mockHttpOutputMessage.getBodyAsString();
    }
}
