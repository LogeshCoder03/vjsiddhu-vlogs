package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminLikeDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestLikeDto;
import com.teachhub.backend.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/likes")
public class AdminLikeController {
    @Autowired
    private LikeService likeService;

    @GetMapping
    public ResponseEntity<Page<AdminLikeDto>> getLikes(@RequestParam(defaultValue = "0") int page){
        Page<AdminLikeDto> likes=likeService.getAllForAdmin(page);
        return ResponseEntity.ok(likes);
    }
    @PostMapping
    public ResponseEntity<ResponseData> createLike(@RequestBody RequestLikeDto requestLikeDto){
        likeService.createForAdmin(requestLikeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Like created successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateLike(@PathVariable Long id,@RequestBody RequestLikeDto requestLikeDto){
        likeService.updateForAdmin(id,requestLikeDto);
        return ResponseEntity.ok(new ResponseData("Like created Successfully"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id){
        likeService.deleteForAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
