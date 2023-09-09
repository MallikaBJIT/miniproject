package com.example.miniproject.controller;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/books/{bookId}/user/{userId}/review")
    public ResponseEntity<?> add(@PathVariable int bookId,
                                 @PathVariable int userId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(bookId, userId, reviewDTO);
        return ResponseEntity.ok("review is added");
    }

    @PutMapping("books/user/{userId}/reviews/{reviewId}/update")
    public ResponseEntity<?> update(@PathVariable int userId,
                                    @PathVariable int reviewId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(userId, reviewId, reviewDTO);
        return ResponseEntity.ok("review is updated");
    }

    @DeleteMapping("/reviews/{reviewId}/user/{userId}/delete")
    public ResponseEntity<?> delete(@PathVariable int reviewId,
                                    @PathVariable int userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok("book is deleted successfully");
    }
//
//    @PostMapping("/books/all")
//    public ResponseEntity<?> get() {
//        return ResponseEntity.ok(reviewService.getAllBooks());
//    }
}
