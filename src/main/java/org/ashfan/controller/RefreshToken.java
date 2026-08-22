package org.ashfan.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ashfan.dto.LoginUserResponseDTO;
import org.ashfan.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token/refresh")
public class RefreshToken {

    @Autowired
    JwtService jwtService;

    @PostMapping
    public ResponseEntity<LoginUserResponseDTO> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization");
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new LoginUserResponseDTO("Refresh token is missing or invalid.", "REFRESH_TOKEN_INVALID", "FAILURE", null, null));
        }

        refreshToken = refreshToken.substring(7); // Remove "Bearer " prefix

        LoginUserResponseDTO response = jwtService.generateNewToken(refreshToken);

        if(response.getStatus().equalsIgnoreCase("FAILURE")) {
            return ResponseEntity.status(401).body(response);
        } else {
            return ResponseEntity.status(200).body(response);
        }

    }
}
