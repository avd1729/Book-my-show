package com.example.mock.entity;

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
@Table(name = "show_times")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int showtimeId;

    public Timestamp startTime;
    public Timestamp endTime;
    public int price;
    public boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movieId", nullable = false)
    public Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "screenId", nullable = false)
    public Screen screen;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Reservation> reservations = new ArrayList<>();
}
