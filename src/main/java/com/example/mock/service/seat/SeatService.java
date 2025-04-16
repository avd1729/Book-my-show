package com.example.mock.service.seat;

import com.example.mock.repo.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService implements ISeatService{
    @Autowired
    private SeatRepository seatRepository;
}
