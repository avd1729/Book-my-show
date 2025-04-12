package com.example.mock.service.user;

import com.example.mock.dto.UserDTO;
import com.example.mock.entity.User;
import com.example.mock.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserDTO userDTO) {
        try {
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new RuntimeException("Username already taken");
            }

            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setAdmin(userDTO.isAdmin());
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(user);
            return "User registered successfully!";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
