package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.UnseenClipsDto;
import com.teachhub.backend.entities.UnseenClips;
import com.teachhub.backend.enums.ShootType;
import com.teachhub.backend.services.UnseenClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/unseen-clips")
public class UnseenClipsController {
    @Autowired
    private UnseenClipService unseenClipService;

    @PostMapping
    public ResponseEntity<ResponseData> createUnseenClip(@RequestParam MultipartFile video,
                                                         @RequestParam ShootType type,
                                                         Authentication authentication) {
        try {
            unseenClipService.create(video, type,authentication.getName());
            return ResponseEntity.ok(new ResponseData("Clips added successfully"));
        }catch (IOException e){
            throw new RuntimeException("Error occurs");
        }
    }

    @GetMapping
    public ResponseEntity<Page<UnseenClipsDto>> getPhotoshoots(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(unseenClipService.getAll(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getVideo(@PathVariable Long id)throws MalformedURLException {
        UnseenClips unseenClips=unseenClipService.getOne(id);
        Path path= Paths.get(unseenClips.getVideoPath());
        Resource resource=new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,"vedio/mp4")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhotoShoot(@PathVariable Long id){
        unseenClipService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
