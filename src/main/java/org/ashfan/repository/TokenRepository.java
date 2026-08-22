package org.ashfan.repository;

import org.ashfan.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByToken(String token);
    TokenEntity findByUserName(String userName);
    void deleteByUserName(String userName);
}
