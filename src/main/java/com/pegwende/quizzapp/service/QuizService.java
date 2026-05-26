package com.pegwende.quizzapp.service;

import com.pegwende.quizzapp.model.Question;
import com.pegwende.quizzapp.model.QuestionWrapper;
import com.pegwende.quizzapp.model.Quiz;
import com.pegwende.quizzapp.model.Response;
import com.pegwende.quizzapp.repo.QuestionRepo;
import com.pegwende.quizzapp.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    QuizRepo quizRepo;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> qlist = questionRepo.getQuestionsForquiz(category, numQ);
            System.out.println(qlist.toString());
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(qlist);
            quizRepo.save(quiz);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionerQuiz(int id) {
        List<QuestionWrapper> flist = new ArrayList<>();
        Optional<Quiz> q = quizRepo.findById(id);
        List<Question>  quest = q.get().getQuestions();

        for ( Question a : quest ) {
            QuestionWrapper qw = new QuestionWrapper(a.getId(),a.getQuestionTitle(), a.getOption1(), a.getOption2(),a.getOption3(),a.getOption4());
            flist.add(qw);
        }
        return new ResponseEntity<>(flist, HttpStatus.OK);
    }

    public ResponseEntity<Integer> summitAnswers(int quizId, List<Response> responses) {
        int score = 0;
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        List<Question> questions =  quiz.get().getQuestions();
        for (Response response : responses) {
            System.out.println(response.toString());
            for(Question q : questions ) {
                if(q.getRightAnswer().equalsIgnoreCase(response.getAnswer())) {
                    score += 1;
                }
            }

        }
        return new ResponseEntity<>(score, HttpStatus.OK);

    }
}
