package com.services.openquestionservice.web.controller;

import com.services.openquestionservice.questionservice.model.OpenQuestion;
import com.services.openquestionservice.questionservice.service.OpenQuestionService;
import com.services.openquestionservice.web.dto.AnswerDto;
import com.services.openquestionservice.web.dto.AnswerResponseDto;
import com.services.openquestionservice.web.dto.OpenQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/open_question")
public class QuestionController {
    @Autowired
    OpenQuestionService openQuestionService;

    @PostMapping("/")
    public ResponseEntity<OpenQuestionDto> createQuestion(@RequestBody OpenQuestionDto openQuestionDto) {
        OpenQuestion openQuestion = openQuestionService.saveQuestion(
                OpenQuestionDto.createDomainObject(openQuestionDto)
        );
        return new ResponseEntity<>(
                OpenQuestionDto.createTransferObject(openQuestion),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{openQuestionId:\\d+}/all_answers")
    public ResponseEntity<AnswerResponseDto> getAllAnswersOfQuestion(@PathVariable Long openQuestionId) {
        OpenQuestion question = openQuestionService.getQuestionById(openQuestionId);
        return new ResponseEntity<>(
                new AnswerResponseDto(
                        question.getQuestionText(),
                        question.getVotingText(),
                        openQuestionService.getAllAnswersByQuestionId(openQuestionId)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{openQuestionId:\\d+}")
    public ResponseEntity<AnswerResponseDto> getAllAnswersOfParticipant(@PathVariable("openQuestionId") Long openQuestionId,
                                           @RequestParam("participantId") Long participantId)
    {
        OpenQuestion question = openQuestionService.getQuestionById(openQuestionId);
        return new ResponseEntity<>(
                new AnswerResponseDto(
                        question.getQuestionText(),
                        question.getVotingText(),
                        openQuestionService.getAllAnswersByQuestionIdAndParticipantId(openQuestionId, participantId)
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{openQuestionId:\\d+}/question_text")
    public ResponseEntity changeQuestionText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String questionText)
    {
        openQuestionService.changeQuestionText(openQuestionId, questionText);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{openQuestionId:\\d+}/voting_text")
    public ResponseEntity changeVotingText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String votingText)
    {
        openQuestionService.changeVotingText(openQuestionId, votingText);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<Long> createAnswer(@RequestBody AnswerDto answerDto) {
        return new ResponseEntity<>(
                openQuestionService.saveAnswer(
                        answerDto.getOpenQuestionId(),
                        answerDto.getParticipantId(),
                        answerDto.getAnswerText()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/answer/{answerId}")
    public ResponseEntity changeAnswer(@PathVariable("answerId") Long answerId, @RequestBody AnswerDto answerDto) {
        openQuestionService.changeAnswer(answerId, answerDto.getAnswerText());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") Long answerId) {
        openQuestionService.deleteAnswer(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/answer/{answerId}/upvote")
    public ResponseEntity upVote(@PathVariable("answerId") Long answerId) {
        openQuestionService.upVote(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/answer/{answerId}/downvote")
    public ResponseEntity downVote(@PathVariable("answerId") Long answerId) {
        openQuestionService.downVote(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
