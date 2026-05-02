package org.ashfan.controller;

import org.ashfan.dto.RegisterUserRequestDTO;
import org.ashfan.dto.RegisterUserResponseDTO;
import org.ashfan.entity.UserEntity;
import org.ashfan.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterUserController {
    @Autowired
    RegisterUserService registerUserService;

    @PostMapping
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {

        RegisterUserResponseDTO response = registerUserService.registerUser(registerUserRequestDTO);
        if("FAILURE".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
