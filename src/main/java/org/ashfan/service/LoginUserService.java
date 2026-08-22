package org.ashfan.service;

import jakarta.transaction.Transactional;
import org.ashfan.dto.LoginUserRequestDTO;
import org.ashfan.dto.LoginUserResponseDTO;
import org.ashfan.entity.TokenEntity;
import org.ashfan.entity.UserEntity;
import org.ashfan.repository.TokenRepository;
import org.ashfan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class LoginUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TokenRepository tokenRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public LoginUserResponseDTO loginUser(LoginUserRequestDTO loginUserRequestDTO) {
        String email = loginUserRequestDTO.getEmail();
        String password = loginUserRequestDTO.getPassword();

        try {
            UserEntity user = userRepository.findByEmail(email);
            if(isEmpty(user)) {
                return new LoginUserResponseDTO("User not found.", "AUTH_USER_NOT_FOUND", "FAILURE", null, null);
            }
            else {
                if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
                    String token = jwtService.generateToken(user.getEmail());
                    String refreshToken = jwtService.generateRefreshToken(user.getEmail(), null);
                    tokenRepository.deleteByUserName(email);
                    tokenRepository.save(new TokenEntity(refreshToken, email));
                    return new LoginUserResponseDTO("Login successful.", "AUTH_SUCCESS", "SUCCESS", token, refreshToken);
                } else {
                    return new LoginUserResponseDTO("Invalid credentials.", "AUTH_INVALID_CREDENTIALS", "FAILURE", null, null);
                }
            }
        } catch (Exception e) {
            return new LoginUserResponseDTO("An error occurred during login.", "AUTH_ERROR", "FAILURE", null, null);
        }
    }

    public boolean validateUser(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

}
