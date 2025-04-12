package com.example.mock.entity;

import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int reservationId;

    public Timestamp reservationTime;

    @Enumerated(EnumType.STRING)
    public ReservationStatus reservationStatus;

    public int totalAmount;

    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtimeId", nullable = false)
    public ShowTime showTime;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ReservedSeat> reservedSeats = new ArrayList<>();

    @OneToOne(mappedBy = "reservation")
    public Payment payment;
}
