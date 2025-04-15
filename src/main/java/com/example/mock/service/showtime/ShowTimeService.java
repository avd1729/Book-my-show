package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.ShowTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeService implements IShowTime{
    @Override
    public ShowTime addShowTime(ShowTimeDTO showTimeDTO) {
        return null;
    }

    @Override
    public ShowTime updateShowTime(ShowTimeDTO showTimeDTO, Integer showTimeId) {
        return null;
    }

    @Override
    public ShowTime deleteShowTime(Integer showTimeId) {
        return null;
    }

    @Override
    public ShowTime getShowTime(Integer showTimeId) {
        return null;
    }

    @Override
    public List<ShowTime> getAllShowTimes() {
        return List.of();
    }

    @Override
    public List<ShowTime> getAllShowTimesByMovie(Integer movieId) {
        return List.of();
    }

    @Override
    public List<ShowTime> getAllShowTimesByScreen(Integer screenId) {
        return List.of();
    }
}
