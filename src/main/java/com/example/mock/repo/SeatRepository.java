package com.example.mock.repo;

import com.example.mock.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByScreen_ScreenIdAndIsActiveTrue(int screenId);
}
