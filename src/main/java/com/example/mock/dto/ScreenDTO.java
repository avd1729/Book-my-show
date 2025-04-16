package com.example.mock.dto;

import com.example.mock.enums.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenDTO {
    private int screenNumber;
    private int seatingCapacity;
    private ScreenType screenType;
    private boolean isActive = true;
    private Integer theaterId;
}
