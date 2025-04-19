package com.example.mock.controller;

import com.example.mock.dto.ReviewDTO;
import com.example.mock.entity.Review;
import com.example.mock.service.review.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO dto) {
        Review review = reviewService.createReview(dto);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable int id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Review> updateReview(@PathVariable int id, @RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {
        Review result = reviewService.getReviewById(id);
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.GONE).body(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable int userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable int movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovieId(movieId));
    }

    @GetMapping("/user/{userId}/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByUserAndMovie(@PathVariable int userId, @PathVariable int movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserAndMovie(userId, movieId));
    }
}
