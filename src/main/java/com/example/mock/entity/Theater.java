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
    public int theaterId;
    public String theaterName;
    public String theaterAddress;
    public String theaterCity;
    public String theaterState;
    public int zipCode;
    public int totalScreens;

    @JsonProperty("isActive")
    public boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Screen> screens = new ArrayList<>();

}
