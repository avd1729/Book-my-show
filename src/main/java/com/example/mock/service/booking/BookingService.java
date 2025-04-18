package com.example.mock.service.booking;

import com.example.mock.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private SeatRepository seatRepository;


    @Override
    public boolean bookSeats(String showtimeId, List<String> seatIds, String userId, long ttlSeconds) {
        return false;
    }
}
