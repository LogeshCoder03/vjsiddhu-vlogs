package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminLikeDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestLikeDto;
import com.teachhub.backend.entities.Comment;
import com.teachhub.backend.entities.Like;
import com.teachhub.backend.entities.Post;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.LikeMapper;
import com.teachhub.backend.repositories.CommentRepository;
import com.teachhub.backend.repositories.LikeRepository;
import com.teachhub.backend.repositories.PostRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;
    private LikeMapper likeMapper;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public Page<AdminLikeDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return likeRepository.findAll(pageable)
                .map(likeMapper::toDto);
    }
    public void createForAdmin(RequestLikeDto requestLikeDto){
        Post post=postRepository.findById(requestLikeDto.getPostId()).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        User user=userRepository.findUserByUsername(requestLikeDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Comment comment=commentRepository.findById(requestLikeDto.getCommentId()).orElseThrow(
                ()->new RuntimeException("Comment not found")
        );
       Like like=new Like();
       like.setPost(post);
       like.setComment(comment);
       like.setUser(user);
       likeRepository.save(like);
    }
    public void updateForAdmin(Long id,RequestLikeDto requestLikeDto){
        Post post=postRepository.findById(requestLikeDto.getPostId()).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        Like like=likeRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        User user=userRepository.findUserByUsername(requestLikeDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Comment comment=commentRepository.findById(requestLikeDto.getCommentId()).orElseThrow(
                ()->new RuntimeException("Comment not found")
        );
       like.setPost(post);
       like.setComment(comment);
       like.setUser(user);
       likeRepository.save(like);
    }
    public void deleteForAdmin(Long id){
        likeRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        likeRepository.deleteById(id);
    }
}
