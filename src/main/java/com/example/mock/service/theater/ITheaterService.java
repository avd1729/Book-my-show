package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheaterService {
    String addTheater(TheaterDTO theaterDTO);
}
