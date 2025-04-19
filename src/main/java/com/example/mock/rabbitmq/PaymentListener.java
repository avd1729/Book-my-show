package com.example.mock.rabbitmq;

import com.example.mock.dto.PaymentDTO;
import com.example.mock.dto.PaymentRequestMessage;
import com.example.mock.dto.ReservationDTO;
import com.example.mock.entity.Payment;
import com.example.mock.entity.Reservation;
import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import com.example.mock.service.payment.PaymentService;
import com.example.mock.service.reservation.ReservationService;
import com.example.mock.service.seat.SeatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PaymentListener {

    private final PaymentService paymentService;
    private final SeatService seatService;
    private final ReservationService reservationService;

    public PaymentListener(PaymentService paymentService, SeatService seatService, ReservationService reservationService) {
        this.paymentService = paymentService;
        this.seatService = seatService;
        this.reservationService = reservationService;
    }

    @RabbitListener(queues = "payment.request.queue")
    public void processPayment(PaymentRequestMessage paymentRequest) {
        try {
            // Retrieve the reservation
            Reservation reservation = reservationService.getReservationById(paymentRequest.getReservationId());
            if (reservation == null) {
                // Log error and potentially send to a dead letter queue
                System.err.println("Reservation not found: " + paymentRequest.getReservationId());
                return;
            }

            ReservationDTO reservationDTO = convertToDTO(reservation);

            // Process payment
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setAmount(paymentRequest.getAmount());
            paymentDTO.setPaymentType(paymentRequest.getPaymentType());
            paymentDTO.setTransactionId(paymentRequest.getTransactionId());
            paymentDTO.setPaymentTime(new Timestamp(paymentRequest.getTimestamp()));
            paymentDTO.setReservationDTO(reservationDTO);

            // Handle actual payment integration here
            // This could be calling a payment gateway API
            boolean paymentSuccessful = processExternalPayment(paymentDTO);

            if (paymentSuccessful) {
                paymentDTO.setPaymentStatus(PaymentStatus.PAID);
                Payment payment = paymentService.addPayment(paymentDTO);

                // Update reservation with payment info
                reservationDTO.setPaymentDTO(paymentDTO);
                reservationDTO.setReservationStatus(ReservationStatus.CONFIRMED);
                reservationDTO.setPaymentStatus(PaymentStatus.PAID);
                reservationService.updateReservation(reservationDTO, reservation.getReservationId());
                System.out.println("payment successful!");
            } else {
                // Payment failed
                paymentDTO.setPaymentStatus(PaymentStatus.PENDING);
                Payment payment = paymentService.addPayment(paymentDTO);

                // Update reservation with failed payment status
                reservationDTO.setPaymentDTO(paymentDTO);
                reservationDTO.setReservationStatus(ReservationStatus.CANCELLED);
                reservationDTO.setPaymentStatus(PaymentStatus.PENDING);
                reservationService.updateReservation(reservationDTO, reservation.getReservationId());


            }

        } catch (Exception e) {
            // Log the error
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            // Could implement retry mechanism or send to dead letter queue
        }
    }

    private boolean processExternalPayment(PaymentDTO paymentDTO) {
        // Implement actual payment processing logic here
        // This would typically integrate with a payment gateway

        // Simulating payment processing with 95% success rate for demo
        return Math.random() < 0.95;
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setReservationId(reservation.getReservationId());
        dto.setUserId(reservation.getUser().getUserId());
        dto.setShowTimeId(reservation.getShowTime().getShowtimeId());
        dto.setReservationStatus(reservation.getReservationStatus());
        dto.setPaymentStatus(reservation.getPaymentStatus());
        dto.setTotalAmount(reservation.getTotalAmount());
        return dto;
    }
}
