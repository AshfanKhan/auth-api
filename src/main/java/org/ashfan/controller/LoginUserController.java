package org.ashfan.controller;

import org.ashfan.dto.LoginUserRequestDTO;
import org.ashfan.dto.LoginUserResponseDTO;
import org.ashfan.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/login")
public class LoginUserController {
    @Autowired
    LoginUserService loginUserService;

    @PostMapping
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        LoginUserResponseDTO response =  loginUserService.loginUser(loginUserRequestDTO);
        if("FAILURE".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
