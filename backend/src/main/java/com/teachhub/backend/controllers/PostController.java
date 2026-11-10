package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.PostsDto.RequestPostDto;
import com.teachhub.backend.dtos.PostsDto.ResponsePostDto;
import com.teachhub.backend.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    @GetMapping
    public Page<ResponsePostDto> getPosts(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        return postService.getAll(page,size);
    }
    @PostMapping
    public ResponseEntity<ResponseData> createPost(@ModelAttribute RequestPostDto requestPostDto,
                                                   @RequestParam(required = false) MultipartFile image,
                                                   Authentication authentication)throws IOException {
        postService.create(requestPostDto,image,authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Post created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDto> getPost(@PathVariable Long id){
        ResponsePostDto responsePostDto=postService.get(id);
        return ResponseEntity.ok(responsePostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deletePost(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<ResponseData> likePost(@PathVariable Long id,Authentication authentication){
        postService.likePost(id, authentication.getName());
        return ResponseEntity.ok(new ResponseData("Post liked successfully"));
    }
}
