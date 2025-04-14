package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MovieDTO {
    public String title;
    public String description;
    public String genre;
    public int duration;
    public String rating;
    public Date releaseDate;
    public Date endDate;
    public String posterUrl;
    public String backdropUrl;
    public String trailerUrl;
    public String language;
}
