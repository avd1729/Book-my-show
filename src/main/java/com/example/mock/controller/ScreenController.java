package com.example.mock.controller;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import com.example.mock.service.screen.ScreenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screen")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenDTO screenDTO){
        Screen result = screenService.addScreen(screenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Screen> updateScreen(@RequestBody ScreenDTO screenDTO, @PathVariable Integer id){
        Screen result = screenService.updateScreen(screenDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Screen> deleteScreen(@PathVariable Integer id){
        Screen result = screenService.deleteScreen(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Screen>> getAllScreens(){
        List<Screen> result = screenService.getAllScreens();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/getAllActive/{id}")
    public ResponseEntity<List<Screen>> getAllActive(@PathVariable Integer id){
        List<Screen> result = screenService.getActiveScreensByTheaterId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
