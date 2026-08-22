package org.ashfan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class TokenEntity {
    private String token;
    private String userName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    public TokenEntity(){};

    public TokenEntity(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }
}
