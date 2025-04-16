package com.example.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "theaters")
public class Theater implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int theaterId;
    private String theaterName;
    private String theaterAddress;
    private String theaterCity;
    private String theaterState;
    private int zipCode;
    private int totalScreens;

    @JsonProperty("isActive")
    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screen> screens = new ArrayList<>();

}
