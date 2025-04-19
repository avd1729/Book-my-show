package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.repo.ScreenRepository;
import com.example.mock.repo.TheaterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService implements ITheaterService{

    private final TheaterRepository theaterRepository;

    private final ScreenRepository screenRepository;

    public TheaterService(TheaterRepository theaterRepository, ScreenRepository screenRepository) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
    }

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

    @Override
    public Theater getTheaterById(Integer theaterId) {
        return theaterRepository.findById(theaterId).orElse(null);
    }

    @Override
    public Theater updateTheater(TheaterDTO theaterDTO, Integer theaterId) {
        Theater theater = getTheaterById(theaterId);
        if(theater != null){
            theater.setTheaterName(theaterDTO.getTheaterName());
            theater.setTheaterAddress(theaterDTO.getTheaterAddress());
            theater.setTheaterCity(theaterDTO.getTheaterCity());
            theater.setTheaterState(theaterDTO.getTheaterState());
            theater.setZipCode(theaterDTO.getZipCode());
            theater.setTotalScreens(theaterDTO.getTotalScreens());
            theater.setActive(theaterDTO.isActive());
            return theaterRepository.save(theater);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Theater deleteTheater(Integer theaterId) {
        Theater theater = getTheaterById(theaterId);
        //theaterRepository.deleteById(theaterId);
        theaterRepository.softDeleteById(theaterId);
        return theater;
    }

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    public List<Theater> getActiveTheaters() {
        return theaterRepository.findAllByIsActiveTrue();
    }

    @Override
    public List<Theater> getTheatersByCity(String theaterCity) {
        return theaterRepository.findAllByTheaterCityAndIsActiveTrue(theaterCity);
    }

    @Override
    public List<Theater> getTheatersByState(String theaterState) {
        return theaterRepository.findAllByTheaterStateAndIsActiveTrue(theaterState);
    }

    @Override
    public List<Screen> getAllScreens(Integer theaterId) {
        return screenRepository.findAllByTheater_TheaterIdAndIsActiveTrue(theaterId);
    }

}
