package com.services.openquestionservice.web.dto;

import com.services.openquestionservice.questionservice.model.Answer;

import java.util.List;

public class AnswerResponseDto {
    private String questionText;
    private String votingText;
    private List<Answer> answers;

    public AnswerResponseDto() {
    }

    public AnswerResponseDto(String questionText, String votingText, List<Answer> answers) {
        this.questionText = questionText;
        this.votingText = votingText;
        this.answers = answers;
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
}
