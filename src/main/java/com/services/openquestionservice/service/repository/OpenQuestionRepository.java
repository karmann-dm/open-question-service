package com.services.openquestionservice.service.repository;

import com.services.openquestionservice.service.model.OpenQuestion;
import org.springframework.data.repository.CrudRepository;

public interface OpenQuestionRepository extends CrudRepository<OpenQuestion, Long> {
}
