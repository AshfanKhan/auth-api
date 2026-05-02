package org.ashfan.service;

import org.ashfan.dto.RegisterUserRequestDTO;
import org.ashfan.dto.RegisterUserResponseDTO;
import org.ashfan.entity.UserEntity;
import org.ashfan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class RegisterUserService {
    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) {

        String name = registerUserRequestDTO.getName();
        String email = registerUserRequestDTO.getEmail();
        String password = registerUserRequestDTO.getPassword();
        String role = registerUserRequestDTO.getRole();

        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(role);


        if(name == null || name.isEmpty() ||
           email == null || email.isEmpty() ||
           password == null || password.isEmpty() ||
           role == null || role.isEmpty()) {
            return new RegisterUserResponseDTO("All fields are required.", "INVALID_REQUEST", "FAILURE");
        }

        //This can be handled in the below catch block to reduce a DB call.
        if(userRepository.existsByEmail(userEntity.getEmail())) {
            return new RegisterUserResponseDTO("Email already registered.", "DUPLICATE_EMAIL", "FAILURE");
        }

        if(!role.equals("ALL1") && !role.equals("ALL2") && !role.equals("ALL3")) {
            return new RegisterUserResponseDTO("Invalid role specified.", "INVALID_ROLE", "FAILURE");
        }
        try {
            userRepository.save(userEntity);
            return new RegisterUserResponseDTO("User registered successfully!", "SUCCESS", "SUCCESS");
        } catch (Exception e) {
            return new RegisterUserResponseDTO("An error occurred during registration.", "SERVER_ERROR", "FAILURE");
        }
    }
}
