package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.Movie;
import com.example.mock.entity.Screen;
import com.example.mock.entity.ShowTime;
import com.example.mock.repo.ShowTimeRepository;
import com.example.mock.service.movie.MovieService;
import com.example.mock.service.screen.ScreenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeService implements IShowTimeService{

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Override
    @Cacheable(value = "showtimes", key = "'by_id'")
    public ShowTime getShowTimeById(Integer showTimeId) {
        return showTimeRepository.findById(showTimeId).orElse(null);
    }

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
        ShowTime showTime = getShowTimeById(showTimeId);
        if(showTime != null){
            showTime.setStartTime(showTimeDTO.getStartTime());
            showTime.setEndTime(showTimeDTO.getEndTime());
            showTime.setPrice(showTimeDTO.getPrice());
            showTime.setActive(showTime.isActive());

            Movie movie = movieService.getById(showTimeDTO.getMovieId());
            showTime.setMovie(movie);
            Screen screen = screenService.getScreenById(showTimeDTO.getScreenId());
            showTime.setScreen(screen);

            return showTimeRepository.save(showTime);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ShowTime deleteShowTime(Integer showTimeId) {
        ShowTime showTime = getShowTimeById(showTimeId);
        showTimeRepository.softDeleteById(showTimeId);
        return showTime;
    }

    @Override
    @Cacheable(value = "showtimes", key = "'all_showtimes'")
    public List<ShowTime> getAllShowTimes() {
        return showTimeRepository.findAll();
    }

    @Override
    @Cacheable(value = "showtimes", key = "#movieId")
    public List<ShowTime> getAllShowTimesByMovie(Integer movieId) {
        return showTimeRepository.findShowTimesByMovieId(movieId);
    }

    @Override
    @Cacheable(value = "showtimes", key = "#screenId")
    public List<ShowTime> getAllShowTimesByScreen(Integer screenId) {
        return showTimeRepository.findShowTimesByScreenId(screenId);
    }
}
