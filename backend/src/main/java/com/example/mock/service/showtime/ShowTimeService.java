package com.example.mock.service.showtime;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.Movie;
import com.example.mock.entity.Screen;
import com.example.mock.entity.ShowTime;
import com.example.mock.exception.ResourceNotFoundException;
import com.example.mock.repo.ShowTimeRepository;
import com.example.mock.service.movie.MovieService;
import com.example.mock.service.screen.ScreenService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeService implements IShowTimeService{

    private final MovieService movieService;
    private final ScreenService screenService;
    private final ShowTimeRepository showTimeRepository;

    public ShowTimeService(MovieService movieService, ScreenService screenService, ShowTimeRepository showTimeRepository) {
        this.movieService = movieService;
        this.screenService = screenService;
        this.showTimeRepository = showTimeRepository;
    }

    @Override
    public ShowTime getShowTimeById(Integer showTimeId) {
        return showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id: " + showTimeId));
    }

    @Override
    public ShowTime addShowTime(ShowTimeDTO showTimeDTO) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(showTimeDTO.getStartTime());
        showTime.setEndTime(showTimeDTO.getEndTime());
        showTime.setPrice(showTimeDTO.getPrice());
        showTime.setActive(showTime.isActive());

        // Check if movie exists
        Movie movie = movieService.getById(showTimeDTO.getMovieId());
        if (movie == null) {
            throw new ResourceNotFoundException("Movie not found with id: " + showTimeDTO.getMovieId());
        }
        showTime.setMovie(movie);

        // Check if screen exists
        Screen screen = screenService.getScreenById(showTimeDTO.getScreenId());
        if (screen == null) {
            throw new ResourceNotFoundException("Screen not found with id: " + showTimeDTO.getScreenId());
        }
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

            // Check if movie exists
            Movie movie = movieService.getById(showTimeDTO.getMovieId());
            if (movie == null) {
                throw new ResourceNotFoundException("Movie not found with id: " + showTimeDTO.getMovieId());
            }
            showTime.setMovie(movie);

            // Check if screen exists
            Screen screen = screenService.getScreenById(showTimeDTO.getScreenId());
            if (screen == null) {
                throw new ResourceNotFoundException("Screen not found with id: " + showTimeDTO.getScreenId());
            }
            showTime.setScreen(screen);

            return showTimeRepository.save(showTime);
        } else {
            throw new ResourceNotFoundException("Showtime not found with id: " + showTimeId);
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
