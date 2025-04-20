package com.example.mock.entity;

import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int reservationId;

    private Timestamp reservationTime;

    @PrePersist
    protected void onCreate() {
        this.reservationTime = new Timestamp(System.currentTimeMillis());
    }

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtimeId", nullable = false)
    private ShowTime showTime;

    @JsonIgnore
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservedSeat> reservedSeats = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "reservation")
    private Payment payment;
}
