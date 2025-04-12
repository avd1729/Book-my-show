package com.example.mock.service.user;

import com.example.mock.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    String registerUser(UserDTO user);
}
