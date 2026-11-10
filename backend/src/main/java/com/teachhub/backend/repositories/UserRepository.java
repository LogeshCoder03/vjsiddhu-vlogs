package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);
}
