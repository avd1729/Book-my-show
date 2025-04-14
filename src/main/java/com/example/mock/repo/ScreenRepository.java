package com.example.mock.repo;

import com.example.mock.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    List<Screen> findAllByTheater_TheaterIdAndIsActiveTrue(int theaterId);

    @Modifying
    @Query("UPDATE Screen s SET s.isActive = false WHERE s.screenId = :id")
    void softDeleteById(@Param("id") int id);
}
