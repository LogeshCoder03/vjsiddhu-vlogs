package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminPostDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestPostDto;
import com.teachhub.backend.dtos.PostsDto.RequestPostDto;
import com.teachhub.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/posts")
public class AdminPostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Page<AdminPostDto>> getPosts(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(postService.getAllForAdmin(page));
    }

    @PostMapping
    public ResponseEntity<ResponseData> createPost(@ModelAttribute AdminRequestPostDto adminRequestPostDto,
                                                   @RequestParam MultipartFile image)throws IOException {
        postService.createForAdmin(adminRequestPostDto,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Post created Successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updatePost(@PathVariable Long id,
                                                   @ModelAttribute AdminRequestPostDto adminRequestPostDto,
                                                   @RequestParam(required = false) MultipartFile image)throws IOException {
        postService.updateForAdmin(id,adminRequestPostDto,image);
        return ResponseEntity.ok(new ResponseData("Post updated Successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deleteForAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
