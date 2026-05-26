package com.pegwende.quizzapp.repo;

import com.pegwende.quizzapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {
}
