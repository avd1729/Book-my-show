package com.example.mock.entity;

import com.example.mock.enums.ScreenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "screens")
public class Screen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screenId;

    private int screenNumber;
    private int seatingCapacity;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    private boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "theater_id", referencedColumnName = "theaterId", nullable = false)
    private Theater theater;

    @JsonIgnore
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTime> showTimes = new ArrayList<>();


}
