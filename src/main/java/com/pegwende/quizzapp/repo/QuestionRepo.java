package com.pegwende.quizzapp.repo;

import com.pegwende.quizzapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);

    @Query(
            value = "SELECT * FROM question q WHERE q.category = :category AND q.difficultyLevel = :difficultyLevel ORDER BY RAND() LIMIT :numQ",
            nativeQuery = true
    )
    List<Question> getQuestionsForquiz(String category, int numQ);
}
