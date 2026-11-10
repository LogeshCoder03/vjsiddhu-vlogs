package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Event;
import com.teachhub.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Page<Event> findEventByUser(User user, Pageable pageable);
    Optional<Event> findEventByUserAndId(User user, Long id);
}
