package com.example.miniproject.repository;

import com.example.miniproject.entity.ReservedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Integer> {
    public boolean existsByUserIdAndBookId(int userId, int bookId);
}
