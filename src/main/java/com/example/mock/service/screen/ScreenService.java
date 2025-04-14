package com.example.mock.service.screen;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.repo.ScreenRepository;
import com.example.mock.repo.TheaterRepository;
import com.example.mock.service.theater.TheaterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService implements IScreenService{


    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private TheaterService theaterService;

    @Override
    public Screen addScreen(ScreenDTO screenDTO) {
        Screen screen = new Screen();
        screen.setScreenNumber(screenDTO.getScreenNumber());
        screen.setSeatingCapacity(screenDTO.getSeatingCapacity());
        screen.setScreenType(screenDTO.getScreenType());
        screen.setActive(screenDTO.isActive());
        Theater theater = theaterRepository.getById(screenDTO.getTheaterId());
        if(theater != null){
            screen.setTheater(theater);
        } else {
            return null;
        }
        return screenRepository.save(screen);

    }

    @Override
    public Screen updateScreen(ScreenDTO screenDTO, Integer screenId) {
        Screen screen = getScreenById(screenId);
        if(screen != null){
            screen.setScreenNumber(screenDTO.getScreenNumber());
            screen.setSeatingCapacity(screenDTO.getSeatingCapacity());
            screen.setScreenType(screenDTO.getScreenType());
            screen.setActive(screenDTO.isActive());
            Theater theater = theaterRepository.getById(screenDTO.getTheaterId());
            if(theater != null){
                screen.setTheater(theater);
            } else {
                return null;
            }
            return screenRepository.save(screen);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Screen deleteScreen(Integer screenId) {
        Screen screen = getScreenById(screenId);
        if(screen != null){
            screenRepository.softDeleteById(screenId);
        }
        return screen;
    }

    @Override
    public Screen getScreenById(Integer screenId) {
        return screenRepository.findById(screenId).orElse(null);
    }

    @Override
    public List<Screen> getActiveScreensByTheaterId(Integer theaterId) {
        return theaterService.getAllScreens(theaterId);
    }

    @Override
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }
}
