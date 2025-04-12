package com.example.mock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reserved_seats")
public class ReservedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int reservedSeatId;
    public int price;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId", nullable = false)
    public Reservation reservation;

    @OneToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "seatId", nullable = false)
    public Seat seat;


}
