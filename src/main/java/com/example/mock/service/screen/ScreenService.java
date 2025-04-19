package com.example.mock.service.screen;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import com.example.mock.repo.ScreenRepository;
import com.example.mock.repo.TheaterRepository;
import com.example.mock.service.theater.TheaterService;
import com.example.mock.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService implements IScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;
    private final TheaterService theaterService;

    public ScreenService(ScreenRepository screenRepository, TheaterRepository theaterRepository, TheaterService theaterService) {
        this.screenRepository = screenRepository;
        this.theaterRepository = theaterRepository;
        this.theaterService = theaterService;
    }

    @Override
    public Screen addScreen(ScreenDTO screenDTO) {
        Theater theater = theaterRepository.findById(screenDTO.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + screenDTO.getTheaterId()));

        Screen screen = new Screen();
        screen.setScreenNumber(screenDTO.getScreenNumber());
        screen.setSeatingCapacity(screenDTO.getSeatingCapacity());
        screen.setScreenType(screenDTO.getScreenType());
        screen.setActive(screenDTO.isActive());
        screen.setTheater(theater);

        return screenRepository.save(screen);
    }

    @Override
    public Screen updateScreen(ScreenDTO screenDTO, Integer screenId) {
        Screen screen = getScreenById(screenId);

        if (screen == null) {
            throw new ResourceNotFoundException("Screen not found with id: " + screenId);
        }

        Theater theater = theaterRepository.findById(screenDTO.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + screenDTO.getTheaterId()));

        screen.setScreenNumber(screenDTO.getScreenNumber());
        screen.setSeatingCapacity(screenDTO.getSeatingCapacity());
        screen.setScreenType(screenDTO.getScreenType());
        screen.setActive(screenDTO.isActive());
        screen.setTheater(theater);

        return screenRepository.save(screen);
    }

    @Override
    @Transactional
    public Screen deleteScreen(Integer screenId) {
        Screen screen = getScreenById(screenId);

        if (screen == null) {
            throw new ResourceNotFoundException("Screen not found with id: " + screenId);
        }

        screenRepository.softDeleteById(screenId);
        return screen;
    }

    @Override
    public Screen getScreenById(Integer screenId) {
        return screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + screenId));
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
