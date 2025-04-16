package com.example.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "show_times")
public class ShowTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int showtimeId;

    public Timestamp startTime;
    public Timestamp endTime;
    public int price;

    @JsonProperty("isActive")
    public boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movieId", nullable = false)
    public Movie movie;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "screenId", nullable = false)
    public Screen screen;

    @JsonIgnore
    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Reservation> reservations = new ArrayList<>();
}
