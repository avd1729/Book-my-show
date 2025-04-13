package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;

import java.util.List;

public interface ITheaterService {
    Theater addTheater(TheaterDTO theaterDTO);
    Theater getTheaterById(Integer theaterId);
    Theater updateTheater(TheaterDTO theater, Integer theaterId);
    List<Theater> getActiveTheaters();
    List<Theater> getTheatersByCity(String theaterCity);
    List<Theater> getTheatersByState(String theaterState);
    List<Screen> getAllScreens(Integer theaterId);
}
