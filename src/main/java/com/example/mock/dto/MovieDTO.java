package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MovieDTO {
    private String title;
    private String description;
    private String genre;
    private int duration;
    private String rating;
    private Date releaseDate;
    private Date endDate;
    private String posterUrl;
    private String backdropUrl;
    private String trailerUrl;
    private String language;
}
