package com.example.mock.controller;

import com.example.mock.dto.MovieDTO;
import com.example.mock.entity.Movie;
import com.example.mock.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

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
}
