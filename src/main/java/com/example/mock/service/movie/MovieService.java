package com.example.mock.service.movie;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService{
    @Override
    public Movie getById(Integer movieId) {
        return null;
    }

    @Override
    public Movie addMovie(MovieDTO movieDTO) {
        return null;
    }

    @Override
    public Movie updateMovie(MovieDTO movieDTO, Integer movieId) {
        return null;
    }

    @Override
    public Movie deleteMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<Movie> getAllMovies() {
        return List.of();
    }

    @Override
    public List<Movie> getByGenre(String genre) {
        return List.of();
    }

    @Override
    public List<Movie> getByLanguage(String language) {
        return List.of();
    }
}
