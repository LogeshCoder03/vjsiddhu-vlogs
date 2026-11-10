package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<UserToken,Long> {
    Optional<UserToken> findByToken(String token);
    void deleteByToken(String token);
}
