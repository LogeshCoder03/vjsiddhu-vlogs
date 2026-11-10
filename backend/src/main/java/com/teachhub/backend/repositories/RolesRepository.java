package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
    Optional<Role> findRoleByRole(String role);
}
