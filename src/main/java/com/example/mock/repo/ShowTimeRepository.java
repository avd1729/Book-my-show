package com.example.mock.repo;

import com.example.mock.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {

    @Modifying
    @Query("UPDATE ShowTime s SET s.isActive = false WHERE s.showtimeId = :id")
    void softDeleteById(@Param("id") int id);

    @Query("SELECT s FROM ShowTime s WHERE s.movie.movieId = :movieId")
    List<ShowTime> findShowTimesByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT s FROM ShowTime s WHERE s.screen.screenId = :screenId")
    List<ShowTime> findShowTimesByScreenId(@Param("screenId") Integer screenId);

    @Query("SELECT s.showtimeId FROM ShowTime s WHERE s.endTime < :now")
    List<Integer> findExpiredShowtimeIds(@Param("now") LocalDateTime now);


}
