package com.example.mock.controller;

import com.example.mock.dto.BookingRequestDTO;
import com.example.mock.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final IBookingService bookingService;

    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookSeats(@RequestBody BookingRequestDTO request) {
        boolean result = bookingService.bookSeats(
                request.getShowTimeId(),
                request.getSeatIds(),
                request.getUserId(),
                request.getAmount(),
                request.getPaymentType(),
                request.getTtlSeconds()
        );

        if (result) {
            return ResponseEntity.ok("Seats booked successfully.");
        } else {
            return ResponseEntity.badRequest().body("Seat booking failed.");
        }
    }
}
