package com.example.miniproject.service;

import com.example.miniproject.dto.ReviewDTO;

public interface ReviewService {
    public void createReview(int bookId, int userId, ReviewDTO reviewDTO);

    public void updateReview(int userId, int reviewId, ReviewDTO reviewDTO);

    public void deleteReview(int reviewId, int userId);

//    public List<ReviewDTO> getAllReviews();
}
