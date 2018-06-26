package com.services.openquestionservice.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/open_question")
public class QuestionController {
    @PostMapping("/")
    public void createQuestion() {}

    @GetMapping("/{openQuestionId}/all_answers")
    public void getAllAnswersOfQuestion(@PathVariable Long openQuestionId) {}

    @GetMapping("/{openQuestionId}")
    public void getAllAnswersOfParticipant(@PathVariable("openQuestionId") Long openQuestionId,
                                           @RequestParam("participantId") Long participantId)
    {}

    @PutMapping("/{openQuestionId}/question_text")
    public void changeQuestionText(@PathVariable("openQuestionId") Long openQuestionId)
    {}

    @PutMapping("/{openQuestionId}/voting_text")
    public void changeVotingText(@PathVariable("openQuestionId") Long openQuestionId)
    {}
}
