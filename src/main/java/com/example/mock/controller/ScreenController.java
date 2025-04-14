package com.example.mock.controller;

import com.example.mock.dto.ScreenDTO;
import com.example.mock.entity.Screen;
import com.example.mock.service.screen.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/screen")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Screen> addsScreen(@RequestBody ScreenDTO screenDTO){
        Screen result = screenService.addScreen(screenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
