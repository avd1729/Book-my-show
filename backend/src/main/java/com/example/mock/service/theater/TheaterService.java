package com.example.mock.service.theater;

import com.example.mock.dto.TheaterDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.exception.ResourceNotFoundException;
import com.example.mock.repo.ScreenRepository;
import com.example.mock.repo.TheaterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService implements ITheaterService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;

    public TheaterService(TheaterRepository theaterRepository, ScreenRepository screenRepository) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public Theater addTheater(TheaterDTO theaterDTO) {
        Theater theater = new Theater();
        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterAddress(theaterDTO.getTheaterAddress());
        theater.setTheaterCity(theaterDTO.getTheaterCity());
        theater.setTheaterState(theaterDTO.getTheaterState());
        theater.setZipCode(theaterDTO.getZipCode());
        theater.setTotalScreens(theaterDTO.getTotalScreens());
        theater.setActive(theaterDTO.isActive());
        return theaterRepository.save(theater);
    }

    @Override
    public Theater getTheaterById(Integer theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with ID: " + theaterId));
    }

    @Override
    public Theater updateTheater(TheaterDTO theaterDTO, Integer theaterId) {
        Theater theater = getTheaterById(theaterId);
        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterAddress(theaterDTO.getTheaterAddress());
        theater.setTheaterCity(theaterDTO.getTheaterCity());
        theater.setTheaterState(theaterDTO.getTheaterState());
        theater.setZipCode(theaterDTO.getZipCode());
        theater.setTotalScreens(theaterDTO.getTotalScreens());
        theater.setActive(theaterDTO.isActive());
        return theaterRepository.save(theater);
    }

    @Override
    @Transactional
    public Theater deleteTheater(Integer theaterId) {
        Theater theater = getTheaterById(theaterId);
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
        List<Theater> theaters = theaterRepository.findAllByTheaterCityAndIsActiveTrue(theaterCity);
        if (theaters.isEmpty()) {
            throw new ResourceNotFoundException("No active theaters found in city: " + theaterCity);
        }
        return theaters;
    }

    @Override
    public List<Theater> getTheatersByState(String theaterState) {
        List<Theater> theaters = theaterRepository.findAllByTheaterStateAndIsActiveTrue(theaterState);
        if (theaters.isEmpty()) {
            throw new ResourceNotFoundException("No active theaters found in state: " + theaterState);
        }
        return theaters;
    }

    @Override
    public List<Screen> getAllScreens(Integer theaterId) {
        List<Screen> screens = screenRepository.findAllByTheater_TheaterIdAndIsActiveTrue(theaterId);
        if (screens.isEmpty()) {
            throw new ResourceNotFoundException("No active screens found for theater ID: " + theaterId);
        }
        return screens;
    }
}
