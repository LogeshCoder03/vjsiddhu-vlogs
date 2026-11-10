package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminPostDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestPostDto;
import com.teachhub.backend.dtos.PostsDto.RequestPostDto;
import com.teachhub.backend.dtos.PostsDto.ResponsePostDto;
import com.teachhub.backend.entities.Like;
import com.teachhub.backend.entities.Post;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.PostMapper;
import com.teachhub.backend.repositories.LikeRepository;
import com.teachhub.backend.repositories.PostRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private PostMapper postMapper;
    public Page<ResponsePostDto> getAll(int pageNumber,int size){
        Pageable pageable=PageRequest.of(pageNumber,size);
        return postRepository.findAll(pageable)
                .map(postMapper::toDto);
    }
    public void create(RequestPostDto requestPostDto, MultipartFile image, String username)throws IOException {
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Post createdPost=postMapper.toEntity(requestPostDto);
        if (image!=null){
            String fileName=image.getOriginalFilename();
            String uploadDir=System.getProperty("user.dir")+"/uploads/";
            String filePath=uploadDir+fileName;
            image.transferTo(new File(filePath));
            createdPost.setImagePath(filePath);
            createdPost.setImage(fileName);
        }
        createdPost.setUser(user);
        postRepository.save(createdPost);
    }
    public ResponsePostDto get(Long id){
        Post post=postRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        return postMapper.toDto(post);
    }
    public void delete(Long id){
        postRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        postRepository.deleteById(id);
    }
    public void likePost(Long id,String username){
        Post post=postRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Optional<Like> like=likeRepository.findLikeByUserAndPost(user,post);
        if(like.isPresent()){
            post.setTotalLikes(post.getTotalLikes()-1);
            postRepository.save(post);
            likeRepository.delete(like.get());
        }
        else{
            Like newLike=new Like();
            newLike.setPost(post);
            newLike.setUser(user);
            likeRepository.save(newLike);
            post.setTotalLikes(post.getTotalLikes()+1);
            postRepository.save(post);
        }
    }
    public Page<AdminPostDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return postRepository.findAll(pageable)
                .map(postMapper::toAdminDto);
    }
    public void createForAdmin(AdminRequestPostDto adminRequestPostDto,MultipartFile image)throws IOException{
        User user=userRepository.findUserByUsername(adminRequestPostDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        image.transferTo(new File(filePath));
        Post createdPost=postMapper.toEntity(adminRequestPostDto);
        createdPost.setUser(user);
        createdPost.setImagePath(filePath);
        createdPost.setImage(fileName);
        postRepository.save(createdPost);
    }
    public void updateForAdmin(Long id,AdminRequestPostDto adminRequestPostDto,MultipartFile image)throws IOException{
        User user=userRepository.findUserByUsername(adminRequestPostDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        postRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        Post createdPost=postMapper.toEntity(adminRequestPostDto);
        createdPost.setUser(user);
        createdPost.setId(id);
        if (image!=null) {
            String fileName=image.getOriginalFilename();
            String uploadDir=System.getProperty("user.dir")+"/uploads/";
            String filePath=uploadDir+fileName;
            image.transferTo(new File(filePath));
            createdPost.setImagePath(filePath);
            createdPost.setImage(fileName);
        }
        postRepository.save(createdPost);
    }
    public void deleteForAdmin(Long id){
        postRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        postRepository.deleteById(id);
    }
}