package com.example.mock.entity;

import com.example.mock.enums.ScreenType;
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
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screenId;

    private int screenNumber;
    private int seatingCapacity;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "theater_id", referencedColumnName = "theaterId", nullable = false)
    private Theater theater;

}
