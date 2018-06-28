package com.services.openquestionservice.web.dto;

import com.services.openquestionservice.questionservice.model.Answer;
import com.services.openquestionservice.questionservice.model.OpenQuestion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OpenQuestionDto {
    @NotNull
    private Long eventId;

    private Long id;

    @NotNull
    @NotBlank
    private String questionText;

    private String votingText;
    private List<Answer> answers;

    public OpenQuestionDto() {
    }

    public OpenQuestionDto(Long eventId, Long id, String questionText, String votingText) {
        this.setEventId(eventId);
        this.setId(id);
        this.setQuestionText(questionText);
        this.setVotingText(votingText);
    }

    public OpenQuestionDto(Long eventId, Long id, String questionText, String votingText, List<Answer> answers) {
        this.eventId = eventId;
        this.id = id;
        this.questionText = questionText;
        this.votingText = votingText;
        this.answers = answers;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getVotingText() {
        return votingText;
    }

    public void setVotingText(String votingText) {
        this.votingText = votingText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public static OpenQuestion createDomainObject(OpenQuestionDto openQuestionDto) {
        OpenQuestion openQuestion = new OpenQuestion();
        openQuestion.setEventId(openQuestionDto.getEventId());
        openQuestion.setQuestionText(openQuestionDto.getQuestionText());
        openQuestion.setVotingText(openQuestionDto.getVotingText());
        return openQuestion;
    }

    public static OpenQuestionDto createTransferObject(OpenQuestion openQuestion) {
        return new OpenQuestionDto(
                openQuestion.getEventId(),
                openQuestion.getId(),
                openQuestion.getQuestionText(),
                openQuestion.getVotingText(),
                openQuestion.getAnswers()
        );
    }
}
