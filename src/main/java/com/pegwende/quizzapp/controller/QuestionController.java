package com.pegwende.quizzapp.controller;

import com.pegwende.quizzapp.model.Question;
import com.pegwende.quizzapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService service;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return service.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return service.getQuestionsByCategory(category);
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById( @PathVariable int id) {
        return service.getQuestionById(id);
    }

    @PostMapping("add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return service.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        return service.deleteQuestion(id);
    }

    @PutMapping ("/update")
    public ResponseEntity<Question> updateQuestion(@RequestBody  Question question) {
        return service.updateQuestion(question);
    }

}
