package com.services.openquestionservice.questionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * JPA model for Answer entity.
 * @author Karmanov Dmitry
 * @version 1.0
 */
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "votes")
    private Integer votes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "open_question_id")
    private OpenQuestion question;

    public Answer() {}

    public Answer(Long participantId, String answerText, OpenQuestion question) {
        this.participantId = participantId;
        this.answerText = answerText;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /**
     * JsonIgnore for preventing recursive output in jackson.
     * @return OpenQuestion
     */
    @JsonIgnore
    public OpenQuestion getQuestion() {
        return question;
    }

    public void setQuestion(OpenQuestion question) {
        this.question = question;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
