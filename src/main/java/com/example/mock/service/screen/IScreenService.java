package com.example.mock.service.screen;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;

import java.util.List;

public interface IScreenService {
    Screen addScreen(ScreenDTO screenDTO);
    Screen updateScreen(ScreenDTO screenDTO, Integer screenId);
    Screen deleteScreen(Integer screenId);
    Screen getScreenById(Integer screenId);
    List<Screen> getActiveScreensByTheaterId(Integer theaterId);
    List<Screen> getAllScreens();
}
