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
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservationService{

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
    public Reservation addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setTotalAmount(reservationDTO.getTotalAmount());
        reservation.setPaymentStatus(reservationDTO.getPaymentStatus());

        User user = userService.getUserById(reservationDTO.getUserId());
        reservation.setUser(user);

        ShowTime showTime = showTimeService.getShowTimeById(reservationDTO.getShowTimeId());
        reservation.setShowTime(showTime);

        // 👇 Only set payment if present
        if (reservationDTO.getPaymentDTO() != null) {
            Payment payment = paymentService.getPaymentById(reservationDTO.getPaymentDTO().getPaymentId());
            reservation.setPayment(payment);
        }

        return reservationRepository.save(reservation);
    }


    public Reservation updateReservation(ReservationDTO reservationDTO, Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if(reservation != null){
            reservation.setReservationStatus(reservationDTO.getReservationStatus());
            reservation.setTotalAmount(reservationDTO.getTotalAmount());
            reservation.setPaymentStatus(reservationDTO.getPaymentStatus());

            User user = userService.getUserById(reservationDTO.getUserId());
            reservation.setUser(user);

            ShowTime showTime = showTimeService.getShowTimeById(reservationDTO.getShowTimeId());
            reservation.setShowTime(showTime);

            Payment payment = paymentService.getPaymentById(reservationDTO.getPaymentDTO().getPaymentId());
            reservation.setPayment(payment);

            return reservationRepository.save(reservation);
        }

        return null;

    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
}
