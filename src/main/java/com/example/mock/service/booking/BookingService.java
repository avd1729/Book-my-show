package com.example.mock.service.booking;

import java.util.UUID;
import com.example.mock.dto.PaymentDTO;
import com.example.mock.dto.ReservationDTO;
import com.example.mock.dto.ReservedSeatDTO;
import com.example.mock.dto.SeatDTO;
import com.example.mock.entity.Payment;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.PaymentType;
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
    public boolean bookSeats(Integer showTimeId, List<Integer> seatIds, Integer userId, int amount, PaymentType paymentType, long ttlSeconds) {

        if (seatIds == null || seatIds.isEmpty()) return false;

        // Step 1: Check seat availability and lock them
        for (Integer seatId : seatIds) {
            if (!seatService.checkAndLockSeat(String.valueOf(showTimeId), String.valueOf(seatId), String.valueOf(seatId), ttlSeconds)) {
                return false;
            }
        }

        // Step 2: Create Reservation (without Payment)
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationStatus(ReservationStatus.PENDING);
        reservationDTO.setTotalAmount(amount);
        reservationDTO.setPaymentStatus(PaymentStatus.PENDING);
        reservationDTO.setUserId(userId);
        reservationDTO.setShowTimeId(showTimeId);

        Reservation reservation = reservationService.addReservation(reservationDTO);
        if (reservation == null) return false;

        // Set reservation ID for further linking
        reservationDTO.setReservationId(reservation.getReservationId());

        // Step 3: Create Payment and link it to Reservation
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(reservation.getTotalAmount());
        paymentDTO.setPaymentType(paymentType);
        paymentDTO.setPaymentStatus(PaymentStatus.PAID);
        paymentDTO.setTransactionId(UUID.randomUUID().toString());
        paymentDTO.setPaymentTime(new Timestamp(System.currentTimeMillis()));
        paymentDTO.setReservationDTO(reservationDTO); // Link payment to reservation

        Payment payment = paymentService.addPayment(paymentDTO);
        if (payment == null) return false;

        // Set payment DTO into reservationDTO and update reservation with payment
        paymentDTO.setPaymentId(payment.getPaymentId());
        reservationDTO.setPaymentDTO(paymentDTO);
        reservationService.updateReservation(reservationDTO, reservation.getReservationId());

        // Update reservation status & payment status after successful payment
        reservationDTO.setReservationStatus(ReservationStatus.CONFIRMED);
        reservationDTO.setPaymentStatus(PaymentStatus.PAID);
        reservationService.updateReservation(reservationDTO, reservation.getReservationId());

        // Step 4: Create Reserved Seats
        int pricePerSeat = amount / seatIds.size();
        for (Integer seatId : seatIds) {
            Seat seat = seatService.getSeatById(seatId);
            if (seat == null) return false;

            ReservedSeatDTO reservedSeatDTO = new ReservedSeatDTO();
            reservedSeatDTO.setPrice(pricePerSeat);
            reservedSeatDTO.setReservationId(reservation.getReservationId());
            reservedSeatDTO.setSeatId(seatId);
            reservedSeatDTO.setShowTimeId(showTimeId);
            System.out.println("Showtime xx"+showTimeId);
            System.out.println("Seat xx" + seat.getSeatId());
            ReservedSeat reservedSeat = reservedSeatService.addReservedSeat(reservedSeatDTO, showTimeId, seat.getSeatId());
            if (reservedSeat == null) return false;

        }

        return true;
    }

}
