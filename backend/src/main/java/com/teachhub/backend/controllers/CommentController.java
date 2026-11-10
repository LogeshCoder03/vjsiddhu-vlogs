package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.CommentDtos.RequestCommentDto;
import com.teachhub.backend.dtos.CommentDtos.ResponseCommentDto;
import com.teachhub.backend.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<ResponseCommentDto>> getComments(@PathVariable Long id,
                                                                Authentication authentication,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        List<ResponseCommentDto> responseCommentDto=commentService.getAll(page,size,authentication.getName(),id);
        return ResponseEntity.ok(responseCommentDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseData> create(@RequestBody RequestCommentDto requestCommentDto,
                                               @PathVariable Long id,
                                               Authentication authentication){
        System.out.println(requestCommentDto);
        commentService.add(requestCommentDto,authentication.getName(),id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Comment added successfully."));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<ResponseData> likeComment(Authentication authentication,@PathVariable Long id){
        commentService.like(id,authentication.getName());
        return ResponseEntity.ok(new ResponseData("Liked successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteComment(@PathVariable(name = "id") Long id,
                                                      Authentication authentication,
                                                      @RequestParam(name = "post_id",required = true) Long postId){
        commentService.delete(id,authentication.getName(),postId);
        return ResponseEntity.ok(new ResponseData("comment deleted"));
    }
}
