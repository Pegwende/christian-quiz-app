package com.pegwende.quizzapp.service;

import com.pegwende.quizzapp.model.Question;
import com.pegwende.quizzapp.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> getQuestionById(int id) {
        try {
            return new ResponseEntity<>(questionRepo.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> addQuestion(Question question) {

        try {
            return new ResponseEntity<>(questionRepo.save(question), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);

    }

    public String deleteQuestion(int id) {
         questionRepo.deleteById(id);
        String message = "question with Id " + id + " deleted";
        System.out.print(message);
        return message;
    }

    public ResponseEntity<Question> updateQuestion(Question question) {
       try {
           return new ResponseEntity<>(questionRepo.save(question), HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
       }
        return new ResponseEntity<>(new Question(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new  ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
