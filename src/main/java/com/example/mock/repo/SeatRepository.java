package com.example.mock.repo;

import com.example.mock.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    // This method returns only the seats that are not reserved (reservedSeat is null) & active.
    List<Seat> findByScreen_ScreenIdAndIsActiveTrueAndReservedSeatIsNull(int screenId);
}
