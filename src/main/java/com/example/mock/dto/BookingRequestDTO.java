package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDTO {
    private String showtimeId;
    private List<String> seatIds;
    private String userId;
    private int amount;
    private long ttlSeconds;
}
