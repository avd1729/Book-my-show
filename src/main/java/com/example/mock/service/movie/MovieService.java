package com.example.mock.service.movie;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import com.example.mock.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MovieService implements IMovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie getById(Integer movieId) {
        return movieRepository.getById(movieId);
    }

    @Override
    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setRating(movieDTO.getRating());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setEndDate(movieDTO.getEndDate());
        movie.setPosterUrl(movieDTO.getPosterUrl());
        movie.setBackdropUrl(movieDTO.getBackdropUrl());
        movie.setTrailerUrl(movieDTO.getTrailerUrl());
        movie.setLanguage(movieDTO.getLanguage());
        return movieRepository.save(movie);
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

    @Override
    public List<Movie> getByDate(Date date) {
        return List.of();
    }
}
