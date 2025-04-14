package com.example.mock.repo;

import com.example.mock.entity.Screen;
import com.example.mock.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findAll();
    List<Theater> findAllByIsActiveTrue();
    List<Theater> findAllByTheaterCityAndIsActiveTrue(String theaterCity);
    List<Theater> findAllByTheaterStateAndIsActiveTrue(String theaterState);

    @Modifying
    @Query("UPDATE Theater t SET t.isActive = false WHERE t.theaterId = :id")
    void softDeleteById(@Param("id") int id);

}
