package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterDTO {
    private String theaterName;
    private String theaterAddress;
    private String theaterCity;
    private String theaterState;
    private int zipCode;
    private int totalScreens;
    private boolean isActive = true;
}
