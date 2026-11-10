package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepository extends JpaRepository<About,Long> {
}
