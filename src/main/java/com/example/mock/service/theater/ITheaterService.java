package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Theater;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheaterService {
    Theater addTheater(TheaterDTO theaterDTO);
    Theater getTheaterById(Integer theaterId);
    Theater updateTheater(TheaterDTO theater, Integer theaterId);
}
