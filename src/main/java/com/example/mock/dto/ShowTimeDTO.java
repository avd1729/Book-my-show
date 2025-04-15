package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ShowTimeDTO {
    public Timestamp startTime;
    public Timestamp endTime;
    public int price;
    public boolean isActive = true;
    public Integer movieId;
    public Integer screenId;
}
