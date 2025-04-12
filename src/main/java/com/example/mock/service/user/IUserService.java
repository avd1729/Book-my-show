package com.example.mock.service.user;

import com.example.mock.dto.UserDTO;
import com.example.mock.entity.User;

public interface IUserService {
    User registerUser(UserDTO user);
}
