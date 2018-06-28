package com.services.openquestionservice.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerDto {
    @NotNull
    private Long openQuestionId;

    @NotNull
    private Long participantId;

    @NotNull
    @NotBlank
    private String answerText;
    
    private Integer votes;

    public AnswerDto() {
    }

    public AnswerDto(Long openQuestionId, Long participantId, String answerText, int votes) {
        this.openQuestionId = openQuestionId;
        this.participantId = participantId;
        this.answerText = answerText;
        this.votes = votes;
    }

    public AnswerDto(Long openQuestionId, Long participantId, String answerText) {
        this.openQuestionId = openQuestionId;
        this.participantId = participantId;
        this.answerText = answerText;
        this.votes = 0;
    }

    public Long getOpenQuestionId() {
        return openQuestionId;
    }

    public void setOpenQuestionId(Long openQuestionId) {
        this.openQuestionId = openQuestionId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
