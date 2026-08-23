package org.ashfan.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ashfan.dto.LoginUserResponseDTO;
import org.ashfan.entity.TokenEntity;
import org.ashfan.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {

    @Autowired
    TokenRepository tokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;

    @Value("${jwt.refresh.expiration}")
    private long jwtRefreshExpirationInMs;

    public JwtService() {
        // Default constructor
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtRefreshSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    // generate JWT with extra claims
    public String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationInMs))
                .signWith(getRefreshSigningKey())
                .compact();
    }

    // extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRefreshUsername(String token) {
        return extractRefreshClaim(token, Claims::getSubject);
    }

    // validate token against a user
    public boolean isValid(String token) {
        return !isExpired(token);
    }

    public boolean isRefreshValid(String token) {
        return !isRefreshExpired(token);
    }

    // check expiry
    private boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private boolean isRefreshExpired(String token) {
        return extractRefreshClaim(token, Claims::getExpiration).before(new Date());
    }

    // generic claim extractor
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private <T> T extractRefreshClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getRefreshSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    public LoginUserResponseDTO generateNewToken(String refreshToken) {
        try {
            String username = extractRefreshUsername(refreshToken);
            TokenEntity token = tokenRepository.findByUserName(username);

            if(token != null && refreshToken.equals(token.getToken()) && isRefreshValid(refreshToken)) {
                String newToken = generateToken(username);
                return new LoginUserResponseDTO("Token refreshed successfully.", "TOKEN_REFRESH_SUCCESS", "SUCCESS", newToken, refreshToken);
            }
            return new LoginUserResponseDTO("Invalid refresh token.", "TOKEN_REFRESH_INVALID", "FAILURE", null, null);
        } catch (Exception e) {
            return new LoginUserResponseDTO("Someting went wrong while refreshing the token.", "TOKEN_REFRESH_ERROR", "FAILURE", null, null);
        }
    }
}
