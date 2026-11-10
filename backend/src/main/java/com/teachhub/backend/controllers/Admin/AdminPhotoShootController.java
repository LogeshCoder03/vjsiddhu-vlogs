package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestPhotoShootDto;
import com.teachhub.backend.dtos.Admin.AdminPhotoShootDto;
import com.teachhub.backend.services.PhotoShootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/photoshoots")
public class AdminPhotoShootController {
    @Autowired
    private PhotoShootService photoShootService;

    @GetMapping
    public ResponseEntity<Page<AdminPhotoShootDto>> getPhotoShoots(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(photoShootService.getAllForAdmin(page));
    }

    @PatchMapping("/publish/{id}")
    public ResponseEntity<ResponseData> publishPhotoShoot(@PathVariable Long id){
        photoShootService.publish(id);
        return ResponseEntity.ok(new ResponseData("Photoshoot published Successfully"));

    }
    @PatchMapping("/unpublish/{id}")
    public ResponseEntity<ResponseData> unPublishPhotoShoots(@PathVariable Long id){
        photoShootService.unPublish(id);
        return ResponseEntity.ok(new ResponseData("Photoshoot unpublished Successfully"));
    }
    @PostMapping
    private ResponseEntity<ResponseData> createPhotoShoot(@ModelAttribute RequestPhotoShootDto requestPhotoShootDto,
                                                          @RequestParam MultipartFile image)throws IOException {
        photoShootService.adminCreate(requestPhotoShootDto,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Photoshoot created Successfully"));
    }
}
