package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.ShowTime;

import java.util.List;

public interface IShowTimeService {
    ShowTime getShowTimeById(Integer showTimeId);
    ShowTime addShowTime(ShowTimeDTO showTimeDTO);
    ShowTime updateShowTime(ShowTimeDTO showTimeDTO, Integer showTimeId);
    ShowTime deleteShowTime(Integer showTimeId);
    List<ShowTime> getAllShowTimes();
    List<ShowTime> getAllShowTimesByMovie(Integer movieId);
    List<ShowTime> getAllShowTimesByScreen(Integer screenId);
}
