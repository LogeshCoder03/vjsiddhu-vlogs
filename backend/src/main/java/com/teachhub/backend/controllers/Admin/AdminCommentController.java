package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestCommentDto;
import com.teachhub.backend.dtos.Admin.AdminCommentDto;
import com.teachhub.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<AdminCommentDto>> getComments(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(commentService.getForAdmin(page));
    }
    @PostMapping
    public ResponseEntity<ResponseData> createContact(@RequestBody AdminRequestCommentDto adminRequestCommentDto){
        commentService.createForAdmin(adminRequestCommentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Comment Created Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateContact(@PathVariable Long id,
                                                      @RequestBody AdminRequestCommentDto adminRequestCommentDto){
        commentService.updateForAdmin(id,adminRequestCommentDto);
        return ResponseEntity.ok(new ResponseData("Comment Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id){
        commentService.deleteForAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
