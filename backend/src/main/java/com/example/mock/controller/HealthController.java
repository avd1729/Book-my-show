package com.example.mock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {

    @GetMapping("/status")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}


