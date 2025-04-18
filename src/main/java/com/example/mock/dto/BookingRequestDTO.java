package com.example.mock.dto;

import com.example.mock.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDTO {
    private Integer showTimeId;
    private List<Integer> seatIds;
    private Integer userId;
    private int amount;
    private PaymentType paymentType;
    private long ttlSeconds;
}
