package com.example.mock.service.reservation;

import com.example.mock.dto.ReservationDTO;
import com.example.mock.entity.Reservation;

public interface IReservationService {
    Reservation addReservation(ReservationDTO reservationDTO);
}
