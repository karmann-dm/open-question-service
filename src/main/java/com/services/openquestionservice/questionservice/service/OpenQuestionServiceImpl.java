package com.services.openquestionservice.questionservice.service;

import com.services.openquestionservice.questionservice.model.Answer;
import com.services.openquestionservice.questionservice.model.OpenQuestion;
import com.services.openquestionservice.questionservice.repository.AnswerRepository;
import com.services.openquestionservice.questionservice.repository.OpenQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenQuestionServiceImpl implements OpenQuestionService {

    @Autowired
    private OpenQuestionRepository openQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public OpenQuestion saveQuestion(OpenQuestion openQuestion) {
        return openQuestionRepository.save(openQuestion);
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(Long questionId) {
        return null;
    }

    @Override
    public List<Answer> getAllAnswersByQuestionIdAndParticipantId(Long questionId, Long participantId) {
        return null;
    }

    @Override
    public void changeQuestionText(Long questionId, String questionText) {

    }

    @Override
    public void changeVotingText(Long questionId, String votingText) {

    }

    @Override
    public Long saveAnswer(Answer answer) {
        return null;
    }

    @Override
    public void changeAnswer(Answer answer) {

    }

    @Override
    public void deleteAnswer(Answer answer) {

    }
}
