package com.example.mock.service.booking;

import com.example.mock.enums.PaymentType;

import java.util.List;

public interface IBookingService {
    boolean bookSeats(String showtimeId, List<String> seatIds, String userId, int amount, PaymentType paymentType, long ttlSeconds);
}
