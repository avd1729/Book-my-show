package com.example.mock.entity;

import com.example.mock.enums.PaymentStatus;
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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private int amount;
    private String paymentType;
    private int transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Timestamp paymentTime;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId", nullable = false)
    private Reservation reservation;
}
