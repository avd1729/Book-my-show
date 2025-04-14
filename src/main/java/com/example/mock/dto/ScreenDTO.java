package com.example.mock.dto;

import com.example.mock.enums.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenDTO {
    public int screenNumber;
    public int seatingCapacity;
    public ScreenType screenType;
    public boolean isActive = true;
    public Integer theaterId;
}
