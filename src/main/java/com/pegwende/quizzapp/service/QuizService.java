package com.pegwende.quizzapp.service;

import com.pegwende.quizzapp.model.*;
import com.pegwende.quizzapp.repo.QuestionRepo;
import com.pegwende.quizzapp.repo.QuizRepo;
import com.pegwende.quizzapp.repo.QuizScoreRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizScoreRepo quizScoreRepo;

    // Add HttpServletRequest to capture the client's network route
    @Autowired
    private HttpServletRequest request;

    private final RestTemplate restTemplate = new RestTemplate();

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

    public ResponseEntity<QuizScore> saveScore(QuizScore quizScore) {
        try {
            quizScore.setId(null);

            // 1. Extract client's IP Address
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            // Handle localhost development environments gracefully
            if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
                quizScore.setLocation("Local Development Host");
            } else {
                // 2. Fetch location from a free, open IP API service
                try {
                    String apiUrl = "http://ip-api.com/json/" + ip.split(",")[0].trim();
                    Map<String, Object> geoData = restTemplate.getForObject(apiUrl, Map.class);

                    if (geoData != null && "success".equals(geoData.get("status"))) {
                        String city = (String) geoData.get("city");
                        String country = (String) geoData.get("country");
                        quizScore.setLocation(city + ", " + country);
                    } else {
                        quizScore.setLocation("Unknown Location");
                    }
                } catch (Exception geoEx) {
                    quizScore.setLocation("Lookup Timeout");
                }
            }

            QuizScore saved = quizScoreRepo.save(quizScore);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuizScore>> getUserScores(UUID userId) {
        List<QuizScore> scores = quizScoreRepo.findByUserId(userId);
        return ResponseEntity.ok(scores);
    }
}
