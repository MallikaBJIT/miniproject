package com.example.miniproject.controller;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("{bookId}/user/{userId}/review")
    public ResponseEntity<?> add(@PathVariable int bookId,
                                 @PathVariable int userId,
                                 @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewService.createReview(bookId, userId, reviewDTO);
        return ResponseHandler.generateResponse("Review is added", HttpStatus.OK);
    }

    @PutMapping("user/{userId}/reviews/{reviewId}/update")
    public ResponseEntity<?> update(@PathVariable int userId,
                                    @PathVariable int reviewId,
                                    @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewService.updateReview(userId, reviewId, reviewDTO);
        return ResponseHandler.generateResponse("Review is updated", HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}/user/{userId}/delete")
    public ResponseEntity<?> delete(@PathVariable int reviewId,
                                    @PathVariable int userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseHandler.generateResponse("Book is deleted successfully", HttpStatus.OK);
    }

    @GetMapping("{bookId}/reviews")
    public ResponseEntity<?> get(@PathVariable int bookId) {
        return ResponseHandler.generateResponse("Book Reviews",
                HttpStatus.OK, reviewService.getReviewByBookId(bookId));
    }
}
