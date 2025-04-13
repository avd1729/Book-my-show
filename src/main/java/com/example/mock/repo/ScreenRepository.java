package com.example.mock.repo;

import com.example.mock.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    List<Screen> findAllByTheater_TheaterIdAndIsActiveTrue(int theaterId);
}
