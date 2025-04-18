package com.example.mock.repo;

import com.example.mock.entity.ReservedSeat;
import com.example.mock.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Integer> {
    boolean existsBySeat(Seat seat);

}
