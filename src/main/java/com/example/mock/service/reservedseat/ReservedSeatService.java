package com.example.mock.service.reservedseat;

import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.dto.SeatDTO;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.repo.ReservedSeatRepository;
import com.example.mock.service.reservation.ReservationService;
import com.example.mock.service.seat.SeatService;
import org.springframework.stereotype.Service;

@Service
public class ReservedSeatService implements IReservedSeatService {

    private final ReservedSeatRepository reservedSeatRepository;
    private final SeatService seatService;
    private final ReservationService reservationService;

    public ReservedSeatService(ReservedSeatRepository reservedSeatRepository,
                               SeatService seatService,
                               ReservationService reservationService) {
        this.reservedSeatRepository = reservedSeatRepository;
        this.seatService = seatService;
        this.reservationService = reservationService;
    }

    @Override
    public ReservedSeat addReservedSeat(ReservedSeatDTO reservedSeatDTO) {
        // Check if seat is already reserved
        SeatDTO seatDTO = reservedSeatDTO.getSeatDTO();
        Seat seat = seatService.getSeatById(seatDTO.getScreenId());

        boolean alreadyReserved = reservedSeatRepository.existsBySeat(seat);
        if (alreadyReserved) {
            throw new RuntimeException("Seat already reserved");
        }

        // Create reservation
        ReservedSeat reservedSeat = new ReservedSeat();
        reservedSeat.setPrice(reservedSeatDTO.getPrice());
        reservedSeat.setSeat(seat);

        Reservation reservation = reservationService.getReservationById(reservedSeatDTO.getReservationId());
        reservedSeat.setReservation(reservation);

        return reservedSeatRepository.save(reservedSeat);
    }

}
