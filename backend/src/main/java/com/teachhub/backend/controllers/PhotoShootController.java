package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.PhotoShootDto;
import com.teachhub.backend.dtos.UnseenClipsDto;
import com.teachhub.backend.enums.ShootType;
import com.teachhub.backend.services.PhotoShootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photoshoots")
public class PhotoShootController {
    @Autowired
    private PhotoShootService photoShootService;

    @PostMapping
    public ResponseEntity<ResponseData> createPhotoshoot(@RequestParam MultipartFile image,
                                                         @RequestParam ShootType type,
                                                         Authentication authentication)throws IOException {
        photoShootService.create(image, type,authentication.getName());
        return ResponseEntity.ok(new ResponseData("Photoshoot added successfully."));
    }

    @GetMapping
    public ResponseEntity<Page<PhotoShootDto>> getPhotoshoots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(photoShootService.getAll(page,size));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePhotoShoot(@PathVariable Long id){
        photoShootService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
