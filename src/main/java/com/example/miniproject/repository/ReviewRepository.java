package com.example.miniproject.repository;

import com.example.miniproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    public List<Review> findAllByBookId(int bookId);

    public boolean existsByUserIdAndBookId(int userId, int bookId);
}
