package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewDTO {
    private Double rating;
    private String reviewText;
    private Timestamp reviewTime;
    private int userId;
    private int movieId;
}
