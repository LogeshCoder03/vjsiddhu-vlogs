package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Comment;
import com.teachhub.backend.entities.Like;
import com.teachhub.backend.entities.Post;
import com.teachhub.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findLikeByUserAndPost(User user, Post post);
    Optional<Like> findLikeByUserAndComment(User user, Comment comment);
}
