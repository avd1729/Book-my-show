package com.example.mock.service.seat;

import com.example.mock.entity.Seat;
import com.example.mock.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService implements ISeatService{
    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getSeatsForScreen(int screenId) {
        return seatRepository.findByScreen_ScreenIdAndIsActiveTrueAndReservedSeatIsNull(screenId);
    }
}
