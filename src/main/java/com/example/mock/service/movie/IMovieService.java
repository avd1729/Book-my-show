package com.example.mock.service.movie;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;

import java.util.Date;
import java.util.List;

public interface IMovieService {
    Movie getById(Integer movieId);
    Movie addMovie(MovieDTO movieDTO);
    Movie updateMovie(MovieDTO movieDTO, Integer movieId);
    Movie deleteMovie(Integer movieId);
    List<Movie> getAllMovies();
    List<Movie> getByGenre(String genre);
    List<Movie> getByLanguage(String language);
    List<Movie> getByGenreAndLanguage(String genre, String language);
    List<Movie> getByDate(Date date);
}
