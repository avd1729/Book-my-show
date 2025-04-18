package com.example.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "reserved_seats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id", "showtime_id"}))
public class ReservedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservedSeatId;
    private int price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId", nullable = false)
    private Reservation reservation;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "seatId", nullable = false)
    private Seat seat;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtimeId", nullable = false)
    private ShowTime showTime;

}
