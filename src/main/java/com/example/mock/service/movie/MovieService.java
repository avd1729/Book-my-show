package com.example.mock.service.movie;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import com.example.mock.repo.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MovieService implements IMovieService{

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getById(Integer movieId) {
        return movieRepository.findById(movieId).orElse(null);
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
        Movie movie = getById(movieId);
        if(movie != null){
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
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Movie deleteMovie(Integer movieId) {
        Movie movie = getById(movieId);
        movieRepository.deleteMovieById(movieId);
        return movie;
    }

    @Override
    @Cacheable(value = "movies", key = "'all_movies'")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }


    @Override
    @Cacheable(value = "movies", key = "#genre")
    public List<Movie> getByGenre(String genre) {
        return movieRepository.getMoviesByGenre(genre);
    }

    @Override
    @Cacheable(value = "movies", key = "#language")
    public List<Movie> getByLanguage(String language) {
        return movieRepository.getMoviesByLanguage(language);
    }

    @Override
    @Cacheable(value = "movies", key = "#genre + '-' + #language")
    public List<Movie> getByGenreAndLanguage(String genre, String language) {
        return movieRepository.getMoviesByGenreAndLanguage(genre, language);
    }

    @Override
    public List<Movie> getByDate(Date date) {
        return movieRepository.findByReleaseDateLessThanEqualAndEndDateGreaterThanEqual(date, date);
    }
}
