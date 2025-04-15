package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.ShowTime;

import java.util.List;

public interface IShowTime {
    ShowTime addShowTime(ShowTimeDTO showTimeDTO);
    ShowTime updateShowTime(ShowTimeDTO showTimeDTO, Integer showTimeId);
    ShowTime deleteShowTime(Integer showTimeId);
    ShowTime getShowTime(Integer showTimeId);
    List<ShowTime> getAllShowTimes();
    List<ShowTime> getAllShowTimesByMovie(Integer movieId);
    List<ShowTime> getAllShowTimesByScreen(Integer screenId);
}
