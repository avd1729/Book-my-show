package com.example.mock.controller;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import com.example.mock.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO){
        Movie result = movieService.addMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
