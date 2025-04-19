package com.example.mock.controller;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import com.example.mock.service.movie.MovieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO){
        Movie result = movieService.addMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/getAll")
    ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> result = movieService.getAllMovies();
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Movie> updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable Integer id){
        Movie result = movieService.updateMovie(movieDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Movie> deleteMovie(@PathVariable Integer id){
        Movie result = movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getByGenre/{genre}")
    ResponseEntity<List<Movie>> getByGenre(@PathVariable String genre){
        List<Movie> result = movieService.getByGenre(genre);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getByLanguage/{language}")
    ResponseEntity<List<Movie>> getByLanguage(@PathVariable String language){
        List<Movie> result = movieService.getByLanguage(language);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getByGenreAndLang/{genre}/{language}")
    ResponseEntity<List<Movie>> getByGenreAndLanguage(@PathVariable String genre, @PathVariable String language){
        List<Movie> result = movieService.getByGenreAndLanguage(genre, language);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/date")
    ResponseEntity<List<Movie>> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date date){
        List<Movie> result = movieService.getByDate(date);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

}
