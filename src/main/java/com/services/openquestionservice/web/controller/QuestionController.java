package com.services.openquestionservice.web.controller;

import com.services.openquestionservice.questionservice.model.Answer;
import com.services.openquestionservice.questionservice.model.OpenQuestion;
import com.services.openquestionservice.questionservice.repository.AnswerRepository;
import com.services.openquestionservice.questionservice.repository.OpenQuestionRepository;
import com.services.openquestionservice.web.dto.AnswerDto;
import com.services.openquestionservice.web.dto.OpenQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/open_question")
public class QuestionController {
    @Autowired
    private OpenQuestionRepository openQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/")
    public ResponseEntity<OpenQuestionDto> createQuestion(@RequestBody OpenQuestionDto openQuestionDto) {
        OpenQuestion openQuestion = OpenQuestionDto.createDomainObject(openQuestionDto);
        openQuestionRepository.save(openQuestion);

        return new ResponseEntity<>(
                OpenQuestionDto.createTransferObject(openQuestion),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{openQuestionId:\\d+}/all_answers")
    public ResponseEntity<List<Answer>> getAllAnswersOfQuestion(@PathVariable Long openQuestionId) {
        OpenQuestion openQuestion = openQuestionRepository.findById(openQuestionId).get();
        return new ResponseEntity<>(openQuestion.getAnswers(), HttpStatus.OK);
    }

    @GetMapping("/{openQuestionId:\\d+}")
    public ResponseEntity<List<Answer>> getAllAnswersOfParticipant(@PathVariable("openQuestionId") Long openQuestionId,
                                           @RequestParam("participantId") Long participantId)
    {
        return new ResponseEntity<>(answerRepository.findAllByQuestionAndParticipantId(openQuestionRepository.findById(openQuestionId).get(), participantId),
                HttpStatus.OK);
    }

    @PutMapping("/{openQuestionId:\\d+}/question_text")
    public ResponseEntity changeQuestionText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String questionText)
    {
        OpenQuestion openQuestion = openQuestionRepository.findById(openQuestionId).get();
        openQuestion.setQuestionText(questionText);
        openQuestionRepository.save(openQuestion);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{openQuestionId:\\d+}/voting_text")
    public ResponseEntity changeVotingText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String votingText)
    {
        OpenQuestion openQuestion = openQuestionRepository.findById(openQuestionId).get();
        openQuestion.setVotingText(votingText);
        openQuestionRepository.save(openQuestion);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<Long> createAnswer(@RequestBody AnswerDto answerDto) {
        OpenQuestion openQuestion = openQuestionRepository.findById(answerDto.getOpenQuestionId()).get();
        Answer answer = new Answer(answerDto.getParticipantId(), answerDto.getAnswerText(), openQuestion);
        answerRepository.save(answer);
        return new ResponseEntity<>(answer.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/answer/{answerId}")
    public void changeAnswer(@PathVariable("answerId") Long answerId) {

    }

    @DeleteMapping("/answer/{answerId}")
    public void deleteAnswer(@PathVariable("answerId") Long answerId) {}

    @PostMapping("/answer/{answerId}/upvote")
    public void upVote(@PathVariable("answerId") Long answerId) {}

    @PostMapping("/answer/{answerId}/downvote")
    public void downVote(@PathVariable("answerId") Long answerId) {}
}
