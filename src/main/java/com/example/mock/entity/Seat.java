package com.example.mock.entity;

import com.example.mock.enums.SeatType;
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
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer seatId;

    public String seatRow;
    public int seatNumber;

    @Enumerated(EnumType.STRING)
    public SeatType seatType;

    public boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "screenId", nullable = false)
    public Screen screen;

    @JsonIgnore
    @OneToOne(mappedBy = "seat")
    public ReservedSeat reservedSeat;

}
