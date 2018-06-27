package com.services.openquestionservice.web.controller;

import com.services.openquestionservice.questionservice.model.OpenQuestion;
import com.services.openquestionservice.questionservice.service.OpenQuestionService;
import com.services.openquestionservice.web.dto.AnswerDto;
import com.services.openquestionservice.web.dto.AnswerResponseDto;
import com.services.openquestionservice.web.dto.OpenQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Provides REST API endpoints for interaction with questions and answers.
 * TODO: Make RabbitMQ message sending.
 * @author Karmanov Dmitry
 * @version 1.0
 */
@RestController
@RequestMapping("/api/open_question")
public class QuestionController {
    @Autowired
    OpenQuestionService openQuestionService;

    /**
     * Question creation endpoint.
     * @param openQuestionDto {@link OpenQuestionDto} DTO passed in POST request.
     * @return ResponseEntity {@link OpenQuestionDto} {@link ResponseEntity} Serialized OpenQuestionDto object.
     */
    @PostMapping("/")
    public ResponseEntity<OpenQuestionDto> createQuestion(@RequestBody OpenQuestionDto openQuestionDto) {
        OpenQuestion openQuestion = openQuestionService.saveQuestion(
                OpenQuestionDto.createDomainObject(openQuestionDto)
        );
        // Making request to events service.
        makeRequestToEventsService(openQuestion.getId());
        return new ResponseEntity<>(
                OpenQuestionDto.createTransferObject(openQuestion),
                HttpStatus.CREATED
        );
    }

    /**
     * Makes request to events service via RestTemplate {@link RestTemplate}
     * @param id Open Question Id {@link OpenQuestion}
     */
    private void makeRequestToEventsService(Long id) {
        // TODO: Check Feign client.
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> request = new HttpEntity<>(id, headers);
        // TODO: Check for events service URL.
        restTemplate.postForEntity("", request, String.class);
    }

    /**
     * Gets all answers with question data by question ID.
     * @param openQuestionId {@link Long} OpenQuestion ID {@link OpenQuestion}.
     * @return ResponseEntity {@link AnswerResponseDto} {@link ResponseEntity} Serialized AnswerResponseDto object.
     */
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

    /**
     * Gets all answers with question data by question ID and participant Id.
     * @param openQuestionId {@link Long} Open Question ID {@link OpenQuestion}.
     * @param participantId {@link Long} Participant ID.
     * @return ResponseEntity {@link AnswerResponseDto} {@link ResponseEntity} Serialized AnswerResponseDto object.
     */
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

    /**
     * Changes question text.
     * @param openQuestionId {@link OpenQuestion} Open Question Id
     * @param questionText {@link String} Question Text
     * @return ResponseEntity {@link ResponseEntity} Http OK status if success.
     */
    @PutMapping("/{openQuestionId:\\d+}/question_text")
    public ResponseEntity changeQuestionText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String questionText)
    {
        openQuestionService.changeQuestionText(openQuestionId, questionText);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Changes voting text.
     * @param openQuestionId Open Question Id {@link OpenQuestion}
     * @param votingText {@link String} Voting text.
     * @return ResponseEntity {@link ResponseEntity} Http OK status if success.
     */
    @PutMapping("/{openQuestionId:\\d+}/voting_text")
    public ResponseEntity changeVotingText(@PathVariable("openQuestionId") Long openQuestionId, @RequestBody String votingText)
    {
        openQuestionService.changeVotingText(openQuestionId, votingText);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Creates answer to question.
     * @param answerDto {@link AnswerDto} Answer DTO payload passed in POST request.
     * @return ResponseEntity {@link ResponseEntity} Http 201 status if success.
     */
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

    /**
     * Changes answer text.
     * @param answerId {@link Long} Answer ID.
     * @param answerDto {@link AnswerDto} Answer DTO payload passed in PUT request.
     * @return ResponseEntity {@link ResponseEntity} Http 200 status if success.
     */
    @PutMapping("/answer/{answerId}")
    public ResponseEntity changeAnswer(@PathVariable("answerId") Long answerId, @RequestBody AnswerDto answerDto) {
        openQuestionService.changeAnswer(answerId, answerDto.getAnswerText());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Deleting answer.
     * @param answerId {@link Long} Answer ID.
     * @return ResponseEntity {@link ResponseEntity} Http 200 status if success.
     */
    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") Long answerId) {
        openQuestionService.deleteAnswer(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Incrementing vote count for answer.
     * @param answerId {@link Long} Answer ID.
     * @return ResponseEntity {@link ResponseEntity} Http 200 status if success.
     */
    @PostMapping("/answer/{answerId}/upvote")
    public ResponseEntity upVote(@PathVariable("answerId") Long answerId) {
        openQuestionService.upVote(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Decrementing vote count for answer.
     * @param answerId {@link Long} Answer ID.
     * @return ResponseEntity {@link ResponseEntity} Http 200 status if success.
     */
    @PostMapping("/answer/{answerId}/downvote")
    public ResponseEntity downVote(@PathVariable("answerId") Long answerId) {
        openQuestionService.downVote(answerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
