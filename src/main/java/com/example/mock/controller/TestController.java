package com.example.mock.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String greet() {
        return "Hello, World!";
    }
}


