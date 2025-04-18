package com.example.mock.service.booking;

import java.util.List;

public interface IBookingService {
    boolean bookSeats(String showtimeId, List<String> seatIds, String userId, long ttlSeconds);
}
