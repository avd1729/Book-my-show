package com.example.mock.service.reservation;

import com.example.mock.dto.ReservationDTO;
import com.example.mock.entity.Payment;
import com.example.mock.entity.Reservation;
import com.example.mock.entity.ShowTime;
import com.example.mock.entity.User;
import com.example.mock.repo.ReservationRepository;
import com.example.mock.service.payment.PaymentService;
import com.example.mock.service.showtime.ShowTimeService;
import com.example.mock.service.user.UserService;
import com.example.mock.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService implements IReservationService {

    private final UserService userService;
    private final ShowTimeService showTimeService;
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;

    public ReservationService(UserService userService, ShowTimeService showTimeService, PaymentService paymentService, ReservationRepository reservationRepository) {
        this.userService = userService;
        this.showTimeService = showTimeService;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public Reservation addReservation(ReservationDTO reservationDTO) {

        User user = userService.getUserById(reservationDTO.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + reservationDTO.getUserId());
        }

        ShowTime showTime = showTimeService.getShowTimeById(reservationDTO.getShowTimeId());
        if (showTime == null) {
            throw new ResourceNotFoundException("ShowTime not found with id: " + reservationDTO.getShowTimeId());
        }

        Reservation reservation = new Reservation();
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setTotalAmount(reservationDTO.getTotalAmount());
        reservation.setPaymentStatus(reservationDTO.getPaymentStatus());
        reservation.setUser(user);
        reservation.setShowTime(showTime);


        if (reservationDTO.getPaymentDTO() != null) {
            Payment payment = paymentService.getPaymentById(reservationDTO.getPaymentDTO().getPaymentId());
            if (payment == null) {
                throw new ResourceNotFoundException("Payment not found with id: " + reservationDTO.getPaymentDTO().getPaymentId());
            }
            reservation.setPayment(payment);
        }

        return reservationRepository.save(reservation);
    }


    @Transactional
    public Reservation updateReservation(ReservationDTO reservationDTO, Integer reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new ResourceNotFoundException("Reservation not found with id: " + reservationId);
        }


        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setTotalAmount(reservationDTO.getTotalAmount());
        reservation.setPaymentStatus(reservationDTO.getPaymentStatus());


        User user = userService.getUserById(reservationDTO.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + reservationDTO.getUserId());
        }
        reservation.setUser(user);

        ShowTime showTime = showTimeService.getShowTimeById(reservationDTO.getShowTimeId());
        if (showTime == null) {
            throw new ResourceNotFoundException("ShowTime not found with id: " + reservationDTO.getShowTimeId());
        }
        reservation.setShowTime(showTime);


        if (reservationDTO.getPaymentDTO() != null) {
            Payment payment = paymentService.getPaymentById(reservationDTO.getPaymentDTO().getPaymentId());
            if (payment == null) {
                throw new ResourceNotFoundException("Payment not found with id: " + reservationDTO.getPaymentDTO().getPaymentId());
            }
            reservation.setPayment(payment);
        }

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }
}
