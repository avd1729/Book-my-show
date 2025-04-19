package com.example.mock.service.reservedseat;

import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.entity.ShowTime;
import com.example.mock.repo.ReservedSeatRepository;
import com.example.mock.service.reservation.ReservationService;
import com.example.mock.service.seat.SeatService;
import com.example.mock.service.showtime.ShowTimeService;
import com.example.mock.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservedSeatService implements IReservedSeatService {

    private final ReservedSeatRepository reservedSeatRepository;
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final ShowTimeService showTimeService;

    private static final Logger logger = LoggerFactory.getLogger(ReservedSeatService.class);

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
    public ReservedSeat addReservedSeat(ReservedSeatDTO reservedSeatDTO, Integer showTimeId, Integer seatId) {

        Seat seat = seatService.getSeatById(seatId);
        ShowTime showTime = showTimeService.getShowTimeById(showTimeId);

        if (seat == null) {
            throw new ResourceNotFoundException("Seat not found with id: " + seatId);
        }

        if (showTime == null) {
            throw new ResourceNotFoundException("Showtime not found with id: " + showTimeId);
        }

        Reservation reservation = reservationService.getReservationById(reservedSeatDTO.getReservationId());
        if (reservation == null) {
            throw new ResourceNotFoundException("Reservation not found with id: " + reservedSeatDTO.getReservationId());
        }

        ReservedSeat reservedSeat = new ReservedSeat();
        reservedSeat.setPrice(reservedSeatDTO.getPrice());
        reservedSeat.setSeat(seat);
        reservedSeat.setReservation(reservation);
        reservedSeat.setShowTime(showTime);

        logger.info("Reserved seat added: Showtime ID = {}, Seat ID = {}", showTime.getShowtimeId(), seat.getSeatId());

        return reservedSeatRepository.save(reservedSeat);
    }
}
