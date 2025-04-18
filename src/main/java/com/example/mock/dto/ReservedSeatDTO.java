package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservedSeatDTO {
    private int reservedSeatId;
    private int price;
    private int reservationId;
    private SeatDTO seatDTO;
}
