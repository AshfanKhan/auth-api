package org.ashfan.service;

import org.ashfan.dto.LoginUserRequestDTO;
import org.ashfan.dto.LoginUserResponseDTO;
import org.ashfan.entity.UserEntity;
import org.ashfan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class LoginUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public LoginUserResponseDTO loginUser(LoginUserRequestDTO loginUserRequestDTO) {
        String email = loginUserRequestDTO.getEmail();
        String password = loginUserRequestDTO.getPassword();

        try {
            UserEntity user = userRepository.findByEmail(email);
            if(isEmpty(user)) {
                return new LoginUserResponseDTO("User not found.", "AUTH_USER_NOT_FOUND", "FAILURE", null);
            }
            else {
                if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
                    String token = jwtService.generateToken(user.getEmail());
                    return new LoginUserResponseDTO("Login successful.", "AUTH_SUCCESS", "SUCCESS", token);
                } else {
                    return new LoginUserResponseDTO("Invalid credentials.", "AUTH_INVALID_CREDENTIALS", "FAILURE", null);
                }
            }
        } catch (Exception e) {
            return new LoginUserResponseDTO("An error occurred during login.", "AUTH_ERROR", "FAILURE", null);
        }
    }

    public boolean validateUser(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

}
