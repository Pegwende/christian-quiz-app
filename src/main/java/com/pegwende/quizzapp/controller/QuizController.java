package com.pegwende.quizzapp.controller;

import com.pegwende.quizzapp.model.QuestionWrapper;
import com.pegwende.quizzapp.model.QuizScore;
import com.pegwende.quizzapp.model.Response;
import com.pegwende.quizzapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService service;



    @GetMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return service.createQuiz(category, numQ, title );
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionerQuiz(@PathVariable int id){
        return service.getQuestionerQuiz(id);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> summitAnswers(@PathVariable int quizId, @RequestBody List<Response> response) {
            return service.summitAnswers(quizId, response);
    }

    @PostMapping("/save")
    public ResponseEntity<QuizScore> saveScore(@RequestBody QuizScore quizScore) {
        return service.saveScore(quizScore);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizScore>> getUserScores(@PathVariable UUID userId) {
        return service.getUserScores(userId);
    }
}
