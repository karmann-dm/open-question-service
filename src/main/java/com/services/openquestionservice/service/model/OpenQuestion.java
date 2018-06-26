package com.services.openquestionservice.service.model;

import com.services.openquestionservice.service.Constants;

import javax.persistence.*;

@Entity
@Table(name = "open_questions")
public class OpenQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "voting_text")
    private String votingText;

    public OpenQuestion() {
    }

    public OpenQuestion(Long eventId, String questionText, String votingText) {
        this.setEventId(eventId);
        this.setQuestionText(questionText);
        this.setVotingText(votingText);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
        if(votingText == null)
            this.votingText = Constants.DEFAULT_VOTING_TEXT;
        else
            this.votingText = votingText;
    }
}
