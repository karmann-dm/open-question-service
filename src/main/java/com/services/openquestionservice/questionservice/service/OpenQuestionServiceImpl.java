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
        OpenQuestion openQuestion = openQuestionRepository.findById(questionId).get();
        return answerRepository.findAllByQuestion(openQuestion);
    }

    @Override
    public List<Answer> getAllAnswersByQuestionIdAndParticipantId(Long questionId, Long participantId) {
        OpenQuestion openQuestion = openQuestionRepository.findById(questionId).get();
        return answerRepository.findAllByQuestionAndParticipantId(openQuestion, participantId);
    }

    @Override
    public void changeQuestionText(Long questionId, String questionText) {
        OpenQuestion openQuestion = openQuestionRepository.findById(questionId).get();
        openQuestion.setQuestionText(questionText);
        openQuestionRepository.save(openQuestion);
    }

    @Override
    public void changeVotingText(Long questionId, String votingText) {
        OpenQuestion openQuestion = openQuestionRepository.findById(questionId).get();
        openQuestion.setVotingText(votingText);
        openQuestionRepository.save(openQuestion);
    }

    @Override
    public Long saveAnswer(Long questionId, Long participantId, String answerText) {
        Answer answer = new Answer();
        answer.setQuestion(openQuestionRepository.findById(questionId).get());
        answer.setParticipantId(participantId);
        answer.setAnswerText(answerText);
        return answerRepository.save(answer).getId();
    }

    @Override
    public void changeAnswer(Long answerId, String answerText) {
        Answer answer = answerRepository.findById(answerId).get();
        answer.setAnswerText(answerText);
        answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long answerId) {
        answerRepository.delete(answerRepository.findById(answerId).get());
    }

    @Override
    public void upVote(Long answerId) {
        Answer answer = answerRepository.findById(answerId).get();
        answer.setVotes(answer.getVotes() + 1);
        answerRepository.save(answer);
    }

    @Override
    public void downVote(Long answerId) {
        Answer answer = answerRepository.findById(answerId).get();
        answer.setVotes(answer.getVotes() - 1);
        answerRepository.save(answer);
    }

    @Override
    public OpenQuestion getQuestionById(Long questionId) {
        return openQuestionRepository.findById(questionId).get();
    }
}
