package com.services.openquestionservice.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/open_question/answer")
public class AnswerController {
    @PostMapping("/")
    public void createAnswer() {}

    @PutMapping("/{answerId}")
    public void changeAnswer(@PathVariable("answerId") Long answerId) {}

    @DeleteMapping("/{answerId}")
    public void deleteAnswer(@PathVariable("answerId") Long answerId) {}

    @PostMapping("/{answerId}/upvote")
    public void upVote(@PathVariable("answerId") Long answerId) {}

    @PostMapping("/{answerId}/downvote")
    public void downVote(@PathVariable("answerId") Long answerId) {}
}
