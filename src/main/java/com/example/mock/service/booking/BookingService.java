package com.example.mock.service.booking;

import com.example.mock.dto.PaymentDTO;
import com.example.mock.dto.ReservationDTO;
import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.dto.SeatDTO;
import com.example.mock.entity.Payment;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import com.example.mock.service.payment.PaymentService;
import com.example.mock.service.reservation.ReservationService;
import com.example.mock.service.reservedseat.ReservedSeatService;
import com.example.mock.service.seat.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingService implements IBookingService {

    private final SeatService seatService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final ReservedSeatService reservedSeatService;

    public BookingService(SeatService seatService, ReservationService reservationService,
                          PaymentService paymentService, ReservedSeatService reservedSeatService) {
        this.seatService = seatService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
        this.reservedSeatService = reservedSeatService;
    }

    @Transactional
    @Override
    public boolean bookSeats(String showtimeId, List<String> seatIds, String userId, int amount, long ttlSeconds) {

        if (seatIds == null || seatIds.isEmpty()) return false;

        // Step 1: Check seat availability and lock them
        for (String seatId : seatIds) {
            if (!seatService.checkAndLockSeat(showtimeId, seatId, userId, ttlSeconds)) {
                return false;
            }
        }

        // Step 2: Create Reservation (without Payment)
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationStatus(ReservationStatus.PENDING);
        reservationDTO.setTotalAmount(amount);
        reservationDTO.setPaymentStatus(PaymentStatus.PENDING);
        reservationDTO.setUserId(Integer.parseInt(userId));
        reservationDTO.setShowTimeId(Integer.parseInt(showtimeId));

        Reservation reservation = reservationService.addReservation(reservationDTO);
        if (reservation == null) return false;

        // Set reservation ID for further linking
        reservationDTO.setReservationId(reservation.getReservationId());

        // Step 3: Create Payment and link it to Reservation
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(amount);
        paymentDTO.setPaymentType("CARD"); // You can replace with actual value
        paymentDTO.setTransactionId(123456); // Replace with actual transaction ID
        paymentDTO.setPaymentTime(new Timestamp(System.currentTimeMillis()));
        paymentDTO.setReservationDTO(reservationDTO); // Link payment to reservation

        Payment payment = paymentService.addPayment(paymentDTO);
        if (payment == null) return false;

        // Set payment DTO into reservationDTO and update reservation with payment
        paymentDTO.setPaymentId(payment.getPaymentId());
        reservationDTO.setPaymentDTO(paymentDTO);
        reservationService.updateReservation(reservationDTO, reservation.getReservationId());

        // Step 4: Create Reserved Seats
        int pricePerSeat = amount / seatIds.size();
        for (String seatId : seatIds) {
            Seat seat = seatService.getSeatById(Integer.parseInt(seatId));
            if (seat == null) return false;

            SeatDTO seatDTO = new SeatDTO();
            seatDTO.setSeatRow(seat.getSeatRow());
            seatDTO.setSeatNumber(seat.getSeatNumber());
            seatDTO.setSeatType(seat.getSeatType());
            seatDTO.setActive(seat.isActive());
            seatDTO.setScreenId(seat.getSeatId());

            ReservedSeatDTO reservedSeatDTO = new ReservedSeatDTO();
            reservedSeatDTO.setPrice(pricePerSeat);
            reservedSeatDTO.setReservationId(reservation.getReservationId());
            reservedSeatDTO.setSeatDTO(seatDTO);

            ReservedSeat reservedSeat = reservedSeatService.addReservedSeat(reservedSeatDTO);
            if (reservedSeat == null) return false;

            seatDTO.setReservedSeatId(reservedSeat.getReservedSeatId());
        }

        return true;
    }
}
