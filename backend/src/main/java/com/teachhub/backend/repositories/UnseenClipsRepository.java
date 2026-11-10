package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.UnseenClips;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnseenClipsRepository extends JpaRepository<UnseenClips,Long> {
    Page<UnseenClips> findUnseenClipsByIsPublished(Boolean isPublished, Pageable pageable);
    Optional<UnseenClips> findUnseenClipsByIsPublishedAndId(Boolean isPublished,Long id);
}
