package com.example.mock.entity;

import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    private Timestamp reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
