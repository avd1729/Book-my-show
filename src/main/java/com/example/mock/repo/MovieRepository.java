package com.example.mock.repo;

import com.example.mock.entity.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Movie m WHERE m.movieId = :movieId")
    void deleteMovieById(@Param("movieId") int movieId);

    List<Movie> getMoviesByGenre(String genre);
    List<Movie> getMoviesByLanguage(String language);
    List<Movie> getMoviesByGenreAndLanguage(String genre, String language);

}
