package com.example.mock.repo;

import com.example.mock.entity.Movie;
import com.example.mock.entity.Review;
import com.example.mock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUser(User user);
    List<Review> findByMovie(Movie movie);
    List<Review> findByUserAndMovie(User user, Movie movie);
}
