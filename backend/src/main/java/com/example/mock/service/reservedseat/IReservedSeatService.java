package com.example.mock.service.reservedseat;

import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.entity.ReservedSeat;

public interface IReservedSeatService {
    ReservedSeat addReservedSeat(ReservedSeatDTO reservedSeatDTO, Integer showTimeId, Integer seatId);
}
