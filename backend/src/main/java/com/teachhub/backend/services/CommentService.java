package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminCommentDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestCommentDto;
import com.teachhub.backend.dtos.CommentDtos.RequestCommentDto;
import com.teachhub.backend.dtos.CommentDtos.ResponseCommentDto;
import com.teachhub.backend.entities.*;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.CommentMapper;
import com.teachhub.backend.repositories.CommentRepository;
import com.teachhub.backend.repositories.LikeRepository;
import com.teachhub.backend.repositories.PostRepository;
import com.teachhub.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private LikeRepository likeRepository;

    public void add(RequestCommentDto requestCommentDto,String username,Long postId){
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Comment comment=commentMapper.toEntity(requestCommentDto);
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }
    public List<ResponseCommentDto> getAll(int page,int size,String username,Long postId){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        Pageable pageable=PageRequest.of(page,size);
        return commentRepository.findCommentByUserAndPost(user,post,pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
    public void delete(Long id,String username,Long postId){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        commentRepository.deleteCommentByUserAndPostAndId(user,post,id);
    }
    public void like(Long id,String username){
        Comment comment=commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Optional<Like> like=likeRepository.findLikeByUserAndComment(user,comment);
        if(like.isPresent()){
            comment.setLikes(comment.getLikes()-1);
            commentRepository.save(comment);
            likeRepository.deleteById(like.get().getId());
        }
        else{
            Like newLike=new Like();
            newLike.setComment(comment);
            newLike.setUser(user);
            likeRepository.save(newLike);
            comment.setLikes(comment.getLikes()+1);
            commentRepository.save(comment);
        }
    }
    public Page<AdminCommentDto> getForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return commentRepository.findAll(pageable)
                .map(commentMapper::toAdminDto);
    };
    public void createForAdmin(AdminRequestCommentDto adminRequestCommentDto){
        Post post=postRepository.findById(adminRequestCommentDto.getPostId()).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        User user=userRepository.findUserByUsername(adminRequestCommentDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Comment comment=commentMapper.toEntity(adminRequestCommentDto);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }
    public void updateForAdmin(Long id,AdminRequestCommentDto adminRequestCommentDto){
        Post post=postRepository.findById(adminRequestCommentDto.getPostId()).orElseThrow(
                ()->new RuntimeException("Post not found")
        );
        User user=userRepository.findUserByUsername(adminRequestCommentDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        Comment comment=commentMapper.toEntity(adminRequestCommentDto);
        comment.setId(id);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }
    public void deleteForAdmin(Long id){
        commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        commentRepository.deleteById(id);
    }
}
