package com.example.mock.controller;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Theater;
import com.example.mock.service.theater.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDTO){
        Theater result = theaterService.addTheater(theaterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
