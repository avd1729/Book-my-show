package com.example.mock.service.review;

import com.example.mock.dto.ReviewDTO;
import com.example.mock.entity.Movie;
import com.example.mock.entity.Review;
import com.example.mock.entity.User;
import com.example.mock.exception.ResourceNotFoundException;
import com.example.mock.repo.MovieRepository;
import com.example.mock.repo.ReviewRepository;
import com.example.mock.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Review createReview(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());
        review.setUser(user);
        review.setMovie(movie);

        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(int id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public Review updateReview(int id, ReviewDTO dto) {
        Review review = getReviewById(id);
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());
        review.setReviewTime(new Timestamp(System.currentTimeMillis()));
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return reviewRepository.findByUser(user);
    }

    @Override
    public List<Review> getReviewsByMovieId(int movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return reviewRepository.findByMovie(movie);
    }

    @Override
    public List<Review> getReviewsByUserAndMovie(int userId, int movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return reviewRepository.findByUserAndMovie(user, movie);
    }
}
