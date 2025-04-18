package com.example.mock.service.booking;

import com.example.mock.repo.PaymentRepository;
import com.example.mock.repo.ReservationRepository;
import com.example.mock.repo.ReservedSeatRepository;
import com.example.mock.repo.SeatRepository;
import com.example.mock.service.seat.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService{

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ReservedSeatRepository reservedSeatRepository;
    private final PaymentRepository paymentRepository;

    private final SeatService seatService;

    public BookingService(SeatRepository seatRepository, ReservationRepository reservationRepository, ReservedSeatRepository reservedSeatRepository, PaymentRepository paymentRepository, SeatService seatService) {
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.reservedSeatRepository = reservedSeatRepository;
        this.paymentRepository = paymentRepository;
        this.seatService = seatService;
    }

    @Override
    public boolean bookSeats(String showtimeId, List<String> seatIds, String userId, long ttlSeconds) {

        boolean result = true;

        // Check seat availability
        for (String seatId: seatIds){
            result = result && seatService.checkAndLockSeat(showtimeId, seatId, userId, 300L);
        }

        if (!result) return false;

        return result;
    }
}
