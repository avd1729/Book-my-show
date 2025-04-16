package com.example.mock.repo;

import com.example.mock.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {
    @Modifying
    @Query("UPDATE ShowTime s SET s.isActive = false WHERE s.showtimeId = :id")
    void softDeleteById(@Param("id") int id);
}
