package com.example.mock.service.review;

import com.example.mock.repo.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

}
