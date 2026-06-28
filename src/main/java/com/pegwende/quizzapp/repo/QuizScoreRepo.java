package com.pegwende.quizzapp.repo;

import com.pegwende.quizzapp.model.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuizScoreRepo extends JpaRepository<QuizScore, Long> {
    // Custom query method so you can fetch scores for a specific user
    List<QuizScore> findByUserId(UUID userId);
}
