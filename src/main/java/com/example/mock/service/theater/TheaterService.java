package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Theater;
import com.example.mock.repo.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService implements ITheaterService{

    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public Theater addTheater(TheaterDTO theaterDTO) {
        try {
            Theater theater = new Theater();
            theater.setTheaterName(theaterDTO.getTheaterName());
            theater.setTheaterAddress(theaterDTO.getTheaterAddress());
            theater.setTheaterCity(theaterDTO.getTheaterCity());
            theater.setTheaterState(theaterDTO.getTheaterState());
            theater.setZipCode(theaterDTO.getZipCode());
            theater.setTotalScreens(theaterDTO.getTotalScreens());
            theater.setActive(theaterDTO.isActive());
            return theaterRepository.save(theater);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
