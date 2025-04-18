package com.example.mock.service.booking;

import com.example.mock.enums.PaymentType;

import java.util.List;

public interface IBookingService {
    boolean bookSeats(Integer showTimeId, List<Integer> seatIds, Integer userId, int amount, PaymentType paymentType, long ttlSeconds);
}
