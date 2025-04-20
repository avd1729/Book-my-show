package com.example.mock.service.seat;

import com.example.mock.entity.Seat;

import java.util.List;

public interface ISeatService {
    List<Seat> getSeatsForScreen(int screenId);
}
