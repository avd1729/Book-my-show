package com.example.mock.dto;

import com.example.mock.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    public String seatRow;
    public int seatNumber;
    public SeatType seatType;
    public boolean isActive = true;
    public Integer screenId;
    public Integer reservedSeatId;
}
