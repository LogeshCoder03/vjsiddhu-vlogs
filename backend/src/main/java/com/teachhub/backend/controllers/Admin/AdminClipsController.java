package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminClipsDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestClipDto;
import com.teachhub.backend.dtos.UnseenClipsDto;
import com.teachhub.backend.services.UnseenClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/clips")
public class AdminClipsController {
    @Autowired
    private UnseenClipService unseenClipService;

    @GetMapping
    public ResponseEntity<Page<AdminClipsDto>> getClips(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(unseenClipService.getAllForAdmin(page));
    }

    @PatchMapping("/publish/{id}")
    public ResponseEntity<ResponseData> publishClips(@PathVariable Long id){
        unseenClipService.publish(id);
        return ResponseEntity.ok(new ResponseData("Clip published"));
    }
    @PatchMapping("/unpublish/{id}")
    public ResponseEntity<ResponseData> unPublishClips(@PathVariable Long id){
        unseenClipService.unPublish(id);
        return ResponseEntity.ok(new ResponseData("Clip unpublished"));
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@RequestParam MultipartFile video,
                                               @ModelAttribute AdminRequestClipDto adminRequestClipDto){
        try{
            unseenClipService.createForAdmin(video,adminRequestClipDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Clip Created Successfully."));
        }
        catch (IOException exception){
            throw new RuntimeException("Error occurs");
        }
    }
}
