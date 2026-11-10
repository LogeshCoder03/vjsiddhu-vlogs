package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievements,Long> {
}
