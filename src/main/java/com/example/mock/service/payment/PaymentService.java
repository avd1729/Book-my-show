package com.example.mock.service.payment;

import com.example.mock.dto.PaymentDTO;
import com.example.mock.entity.Payment;
import com.example.mock.entity.Reservation;
import com.example.mock.repo.PaymentRepository;
import com.example.mock.repo.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {


    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Payment addPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setPaymentTime(paymentDTO.getPaymentTime());

        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationDTO().getReservationId()).orElse(null);
        if (reservation != null) {
            payment.setReservation(reservation);
            payment.setAmount(reservation.getTotalAmount());
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        } else {
            throw new RuntimeException("Reservation not found");
        }

        return paymentRepository.save(payment);
    }


    public Payment getPaymentById(Integer paymentId){
        return paymentRepository.findById(paymentId).orElse(null);
    }
}
