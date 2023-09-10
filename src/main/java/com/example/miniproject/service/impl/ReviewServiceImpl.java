package com.example.miniproject.service.impl;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.entity.Book;
import com.example.miniproject.entity.Review;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.repository.ReviewRepository;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public void createReview(int bookId, int userId, ReviewDTO reviewDTO) {
        if (reviewRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new CustomException("You are not allowed to review again", HttpStatus.CONFLICT);
        }
        Review review = modelMapper.map(reviewDTO, Review.class);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException("Book id not exist", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User id not exist", HttpStatus.NOT_FOUND));
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);
    }

    @Override
    public void updateReview(int userId, int reviewId, ReviewDTO reviewDTO) {
        Review review = getReviewById(reviewId);
        if (review.getUser().getId() != userId) {
            throw new CustomException("User id does not matched", HttpStatus.NOT_FOUND);
        }
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(int reviewId, int userId) {
        Review review = getReviewById(reviewId);
        if (review.getUser().getId() != userId) {
            throw new CustomException("User id does not matched", HttpStatus.NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDTO> getReviewByBookId(int bookId) {
        return reviewRepository.findAllByBookId(bookId).stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }

    private Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException("Review not found", HttpStatus.NOT_FOUND));
    }
}
