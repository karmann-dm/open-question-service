package com.services.openquestionservice.service.repository;

import com.services.openquestionservice.service.model.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
