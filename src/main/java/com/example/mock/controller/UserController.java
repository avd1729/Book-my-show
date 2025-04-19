package com.example.mock.controller;

import com.example.mock.dto.UserDTO;
import com.example.mock.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        String result = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
