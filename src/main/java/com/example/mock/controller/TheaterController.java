package com.example.mock.controller;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.service.theater.TheaterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDTO){
        Theater result = theaterService.addTheater(theaterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> updateTheater(@RequestBody TheaterDTO theaterDTO, @PathVariable Integer id){
        Theater result = theaterService.updateTheater(theaterDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Theater>> getAllTheaters(){
        List<Theater> result = theaterService.getAllTheaters();
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> deleteTheater(@PathVariable Integer id){
        Theater result = theaterService.deleteTheater(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Integer id){
        Theater result = theaterService.getTheaterById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<Theater>> getActiveTheaters(){
        List<Theater> result = theaterService.getActiveTheaters();
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getByCity/{city}")
    public ResponseEntity<List<Theater>> getTheatersByCity(@PathVariable String city){
        List<Theater> result = theaterService.getTheatersByCity(city);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/getByState/{state}")
    public ResponseEntity<List<Theater>> getTheatersByState(@PathVariable String state){
        List<Theater> result = theaterService.getTheatersByState(state);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/screens/{id}")
    public ResponseEntity<List<Screen>> getScreens(@PathVariable Integer id){
        List<Screen> result = theaterService.getAllScreens(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

}
