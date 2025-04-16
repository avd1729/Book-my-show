package com.example.mock.controller;

import com.example.mock.entity.Seat;
import com.example.mock.service.seat.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/active/{id}")
    ResponseEntity<List<Seat>> getSeatsForScreen(@PathVariable Integer id){
        List<Seat> result = seatService.getSeatsForScreen(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }
}
