package com.example.mock.entity;

import com.example.mock.enums.ScreenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int screenId;

    public int screenNumber;
    public int seatingCapacity;

    @Enumerated(EnumType.STRING)
    public ScreenType screenType;

    public boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "theater_id", referencedColumnName = "theaterId", nullable = false)
    public Theater theater;

    @JsonIgnore
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Seat> seats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ShowTime> showTimes = new ArrayList<>();


}
