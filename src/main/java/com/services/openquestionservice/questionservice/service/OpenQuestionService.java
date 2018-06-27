package com.services.openquestionservice.questionservice.service;

import com.services.openquestionservice.questionservice.model.Answer;
import com.services.openquestionservice.questionservice.model.OpenQuestion;

import java.util.List;

public interface OpenQuestionService {
    OpenQuestion saveQuestion(OpenQuestion openQuestion);
    List<Answer> getAllAnswersByQuestionId(Long questionId);
    List<Answer> getAllAnswersByQuestionIdAndParticipantId(Long questionId, Long participantId);
    void changeQuestionText(Long questionId, String questionText);
    void changeVotingText(Long questionId, String votingText);
    Long saveAnswer(Long questionId, Long participantId, String answerText);
    void changeAnswer(Long answerId, String answerText);
    void deleteAnswer(Long answerId);
    void upVote(Long answerId);
    void downVote(Long answerId);

    OpenQuestion getQuestionById(Long questionId);
}
