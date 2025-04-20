package com.example.mock.controller;

import com.example.mock.entity.Seat;
import com.example.mock.service.seat.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/active/{id}")
    ResponseEntity<List<Seat>> getSeatsForScreen(@PathVariable Integer id){
        List<Seat> result = seatService.getSeatsForScreen(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/lock")
    public ResponseEntity<String> lockSeat(
            @RequestParam String showtimeId,
            @RequestParam String seatId,
            @RequestParam String userId,
            @RequestParam(defaultValue = "300") long ttlSeconds
    ) {
        boolean success = seatService.lockSeat(showtimeId, seatId, userId, ttlSeconds);
        if (success) {
            return ResponseEntity.ok("Seat locked successfully.");
        } else {
            return ResponseEntity.status(409).body("Seat is already locked by another user.");
        }
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockSeat(
            @RequestParam String showtimeId,
            @RequestParam String seatId,
            @RequestParam String userId
    ) {
        boolean success = seatService.unlockSeat(showtimeId, seatId, userId);
        if (success) {
            return ResponseEntity.ok("Seat unlocked successfully.");
        } else {
            return ResponseEntity.status(403).body("Failed to unlock seat. You might not be the owner.");
        }
    }

}
