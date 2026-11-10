package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.PhotoShoots;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoShootRepository extends JpaRepository<PhotoShoots,Long> {
    Page<PhotoShoots> findPhotoShootsByIsPublished(Boolean isPublished, Pageable pageable);
}
