package com.teachhub.backend.repositories;

import com.teachhub.backend.entities.Comment;
import com.teachhub.backend.entities.Like;
import com.teachhub.backend.entities.Post;
import com.teachhub.backend.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByUserAndPost(User user, Post post, Pageable pageable);

    void deleteCommentByUserAndPostAndId(User user, Post post, Long id);
}
