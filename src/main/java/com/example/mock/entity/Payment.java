package com.example.mock.entity;

import com.example.mock.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public int paymentId;

    public int amount;
    public String paymentType;
    public int transactionId;

    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus;

    public Timestamp paymentTime;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId", nullable = false)
    public Reservation reservation;
}
