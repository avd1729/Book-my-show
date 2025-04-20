package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ShowTimeDTO {
    private Timestamp startTime;
    private Timestamp endTime;
    private int price;
    private boolean isActive = true;
    private Integer movieId;
    private Integer screenId;
}
