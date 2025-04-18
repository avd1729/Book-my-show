package com.example.mock.repo;

import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import com.example.mock.entity.ShowTime;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Integer> {
    boolean existsBySeat(Seat seat);
    // Find all reserved seats
    List<ReservedSeat> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM ReservedSeat rs WHERE rs.reservation.reservationId IN :reservationIds")
    void deleteByReservationIds(@Param("reservationIds") List<Integer> reservationIds);

    // In ReservedSeatRepository
    boolean existsBySeatAndShowTime(Seat seat, ShowTime showTime);

}
