package com.example.mock.service.booking;

import java.util.UUID;

import com.example.mock.dto.*;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingService implements IBookingService {

    private final SeatService seatService;
    private final ReservationService reservationService;
    private final ReservedSeatService reservedSeatService;
    private final RabbitTemplate rabbitTemplate;

    public BookingService(SeatService seatService, ReservationService reservationService,
                          ReservedSeatService reservedSeatService, RabbitTemplate rabbitTemplate) {
        this.seatService = seatService;
        this.reservationService = reservationService;
        this.reservedSeatService = reservedSeatService;
        this.rabbitTemplate = rabbitTemplate;
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

        // Step 2: Create Reservation (with PENDING status)
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationStatus(ReservationStatus.PENDING);
        reservationDTO.setTotalAmount(amount);
        reservationDTO.setPaymentStatus(PaymentStatus.PENDING);
        reservationDTO.setUserId(userId);
        reservationDTO.setShowTimeId(showTimeId);

        Reservation reservation = reservationService.addReservation(reservationDTO);
        if (reservation == null) return false;

        // Step 3: Create Reserved Seats
        int pricePerSeat = amount / seatIds.size();
        for (Integer seatId : seatIds) {
            Seat seat = seatService.getSeatById(seatId);
            if (seat == null) return false;

            ReservedSeatDTO reservedSeatDTO = new ReservedSeatDTO();
            reservedSeatDTO.setPrice(pricePerSeat);
            reservedSeatDTO.setReservationId(reservation.getReservationId());
            reservedSeatDTO.setSeatId(seatId);
            reservedSeatDTO.setShowTimeId(showTimeId);
            ReservedSeat reservedSeat = reservedSeatService.addReservedSeat(reservedSeatDTO, showTimeId, seat.getSeatId());
            if (reservedSeat == null) return false;
        }

        // Step 4: Send payment request to RabbitMQ
        PaymentRequestMessage paymentRequest = new PaymentRequestMessage();
        paymentRequest.setReservationId(reservation.getReservationId());
        paymentRequest.setAmount(amount);
        paymentRequest.setPaymentType(paymentType);
        paymentRequest.setUserId(userId);
        paymentRequest.setTimestamp(System.currentTimeMillis());
        paymentRequest.setTransactionId(UUID.randomUUID().toString());

        // Send to RabbitMQ queue for asynchronous processing
        rabbitTemplate.convertAndSend("payment-exchange", "payment.request", paymentRequest);

        return true;
    }

}
