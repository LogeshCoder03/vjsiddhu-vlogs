package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Feast;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.entities.UserFeast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFeastRepository extends JpaRepository<UserFeast,Long> {
    Page<UserFeast> findUserFeastByUser(User user, Pageable pageable);
    void deleteUserFeastByUserAndId(User user, Long id);
    Optional<UserFeast> findByMobileNumber(Long mobileNumber);
    Boolean existsUserFeastByUserAndFeast(User user, Feast feast);
}
