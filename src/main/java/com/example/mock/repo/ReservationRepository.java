package com.example.mock.repo;

import com.example.mock.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r.reservationId FROM Reservation r WHERE r.showTime.showtimeId IN :showtimeIds")
    List<Integer> findReservationIdsByShowtimeIds(@Param("showtimeIds") List<Integer> showtimeIds);

}
