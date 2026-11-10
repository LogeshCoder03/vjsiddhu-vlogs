package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.AchievementDto;
import com.teachhub.backend.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {
    @Autowired
    private AchievementService achievementService;

    @GetMapping
    public List<AchievementDto> getAchievements(){
        return achievementService.getAll();
    }

    @PostMapping
    private ResponseEntity<ResponseData> createAchievement(Authentication authentication,
                                                           @RequestBody AchievementDto achievementDto){
        achievementService.create(achievementDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("About of the member created successfully"));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAchievement(@PathVariable Long id){
        achievementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
