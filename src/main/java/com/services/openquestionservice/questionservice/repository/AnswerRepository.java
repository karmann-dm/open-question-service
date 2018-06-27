package com.services.openquestionservice.questionservice.repository;

import com.services.openquestionservice.questionservice.model.Answer;
import com.services.openquestionservice.questionservice.model.OpenQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByQuestionAndParticipantId(OpenQuestion question, Long participantId);
    List<Answer> findAllByQuestion(OpenQuestion question);
}
