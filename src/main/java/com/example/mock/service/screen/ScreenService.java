package com.example.mock.service.screen;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService implements IScreenService{

    @Override
    public Screen addScreen(ScreenDTO screenDTO) {
        return null;
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
