package com.services.openquestionservice.questionservice.repository;

import com.services.openquestionservice.questionservice.model.OpenQuestion;
import org.springframework.data.repository.CrudRepository;

public interface OpenQuestionRepository extends CrudRepository<OpenQuestion, Long> {
}
