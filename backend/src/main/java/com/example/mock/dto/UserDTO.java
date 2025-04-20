package com.example.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private boolean isAdmin = false;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
