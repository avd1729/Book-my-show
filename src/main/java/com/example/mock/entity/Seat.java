package com.example.mock.entity;

import com.example.mock.enums.SeatType;
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
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue
    private long seatId;

    private String seatRow;
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "screenId", nullable = false)
    private Screen screen;

}
