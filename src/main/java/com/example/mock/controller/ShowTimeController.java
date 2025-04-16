package com.example.mock.controller;

import com.example.mock.dto.ShowTimeDTO;
import com.example.mock.entity.ShowTime;
import com.example.mock.service.showtime.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtime")
public class ShowTimeController {

    @Autowired
    private ShowTimeService showTimeService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ShowTime> addShowTime(@RequestBody ShowTimeDTO showTimeDTO){
        ShowTime result = showTimeService.addShowTime(showTimeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/all")
    ResponseEntity<List<ShowTime>> getAllShowTimes(){
        List<ShowTime> result = showTimeService.getAllShowTimes();
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ShowTime> updateShowTime(@RequestBody ShowTimeDTO showTimeDTO, @PathVariable Integer id){
        ShowTime result = showTimeService.updateShowTime(showTimeDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ShowTime> deleteShowTime(@PathVariable Integer id){
        ShowTime result = showTimeService.deleteShowTime(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/movie/{id}")
    ResponseEntity<List<ShowTime>> getAllShowTimesByMovie(@PathVariable Integer id){
        List<ShowTime> result = showTimeService.getAllShowTimesByMovie(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/screen/{id}")
    ResponseEntity<List<ShowTime>> getAllShowTimesByScreen(@PathVariable Integer id){
        List<ShowTime> result = showTimeService.getAllShowTimesByScreen(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

}
