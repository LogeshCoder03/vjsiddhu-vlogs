package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Feast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeastRepository extends JpaRepository<Feast,Long> {
    Page<Feast> findByIsLaunchedTrue(Pageable pageable);
//    List<Feast> findFeastByIsLaunchedTrueAndStatus_
}
