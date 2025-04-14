package com.example.mock.service.screen;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.repo.ScreenRepository;
import com.example.mock.repo.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService implements IScreenService{


    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

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
        return null;
    }

    @Override
    public Screen deleteScreen(Integer screenId) {
        return null;
    }

    @Override
    public Screen getScreenById(Integer screenId) {
        return null;
    }

    @Override
    public Screen getActiveScreensByTheaterId(Integer theaterId) {
        return null;
    }

    @Override
    public List<Screen> getAllScreens() {
        return List.of();
    }
}
