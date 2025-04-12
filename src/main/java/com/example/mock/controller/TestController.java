package com.example.mock.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String greet() {
        return "Hello, World!";
    }
}


