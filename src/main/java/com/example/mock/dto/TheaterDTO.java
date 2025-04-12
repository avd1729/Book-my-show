package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterDTO {
    public String theaterName;
    public String theaterAddress;
    public String theaterCity;
    public String theaterState;
    public int zipCode;
    public int totalScreens;
    public boolean isActive = true;
}
