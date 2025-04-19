package com.example.mock.service.review;

import com.example.mock.dto.ReviewDTO;
import com.example.mock.entity.Review;

import java.util.List;

public interface IReviewService {
    Review createReview(ReviewDTO dto);
    Review getReviewById(int id);
    Review updateReview(int id, ReviewDTO dto);
    void deleteReview(int id);
    List<Review> getReviewsByUserId(int userId);
    List<Review> getReviewsByMovieId(int movieId);
    List<Review> getReviewsByUserAndMovie(int userId, int movieId);
}
