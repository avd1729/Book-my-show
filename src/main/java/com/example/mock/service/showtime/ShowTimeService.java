package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.Movie;
import com.example.mock.entity.Screen;
import com.example.mock.entity.ShowTime;
import com.example.mock.repo.ShowTimeRepository;
import com.example.mock.service.movie.MovieService;
import com.example.mock.service.screen.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeService implements IShowTime{

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Override
    public ShowTime addShowTime(ShowTimeDTO showTimeDTO) {

        ShowTime showTime = new ShowTime();
        showTime.setStartTime(showTimeDTO.getStartTime());
        showTime.setEndTime(showTimeDTO.getEndTime());
        showTime.setPrice(showTimeDTO.getPrice());
        showTime.setActive(showTime.isActive());

        Movie movie = movieService.getById(showTimeDTO.getMovieId());
        showTime.setMovie(movie);
        Screen screen = screenService.getScreenById(showTimeDTO.getScreenId());
        showTime.setScreen(screen);

        return showTimeRepository.save(showTime);

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
        return showTimeRepository.findAll();
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
