package com.example.mock.service.reservedseat;

import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.dto.SeatDTO;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.entity.ShowTime;
import com.example.mock.repo.ReservedSeatRepository;
import com.example.mock.service.reservation.ReservationService;
import com.example.mock.service.seat.SeatService;
import com.example.mock.service.showtime.ShowTimeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservedSeatService implements IReservedSeatService {

    private final ReservedSeatRepository reservedSeatRepository;
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final ShowTimeService showTimeService;

    public ReservedSeatService(ReservedSeatRepository reservedSeatRepository,
                               SeatService seatService,
                               ReservationService reservationService, ShowTimeService showTimeService) {
        this.reservedSeatRepository = reservedSeatRepository;
        this.seatService = seatService;
        this.reservationService = reservationService;
        this.showTimeService = showTimeService;
    }

    @Override
    @Transactional
    public ReservedSeat addReservedSeat(ReservedSeatDTO reservedSeatDTO,Integer showTimeId, Integer seatId) {
        // Retrieve the seat and showtime
        Seat seat = seatService.getSeatById(seatId);
        ShowTime showTime = showTimeService.getShowTimeById(showTimeId);

        // Create the ReservedSeat
        ReservedSeat reservedSeat = new ReservedSeat();
        reservedSeat.setPrice(reservedSeatDTO.getPrice());
        reservedSeat.setSeat(seat);

        Reservation reservation = reservationService.getReservationById(reservedSeatDTO.getReservationId());
        reservedSeat.setReservation(reservation);
        reservedSeat.setShowTime(showTime);

        System.out.println("Showtime "+showTime.getShowtimeId());
        System.out.println("Seat" + seat.getSeatId());

        return reservedSeatRepository.save(reservedSeat);
    }


}
