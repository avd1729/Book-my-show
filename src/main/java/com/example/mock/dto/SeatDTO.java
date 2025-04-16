package com.example.mock.dto;

import com.example.mock.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    private String seatRow;
    private int seatNumber;
    private SeatType seatType;
    private boolean isActive = true;
    private Integer screenId;
    private Integer reservedSeatId;
}
