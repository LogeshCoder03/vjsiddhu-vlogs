package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminAchievementDto;
import com.teachhub.backend.dtos.Admin.AdminClipsDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestAchievementDto;
import com.teachhub.backend.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/achievements")
public class AdminAchievementController {
    @Autowired
    private AchievementService achievementService;

    @GetMapping
    public ResponseEntity<Page<AdminAchievementDto>> getAchievements(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(achievementService.getAllForAdmin(page));
    }

    @PostMapping
    public ResponseEntity<ResponseData> createAchievement(@RequestBody RequestAchievementDto requestAchievementDto){
        achievementService.adminCreation(requestAchievementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Achievement created successfully."));
    }

    @PutMapping("/{id}")
    private ResponseEntity<ResponseData> updateAchievement(@PathVariable Long id,
                                                           @RequestBody RequestAchievementDto requestAchievementDto){
        achievementService.update(id,requestAchievementDto);
        return ResponseEntity.ok(new ResponseData("Achievement updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAchievement(@PathVariable  Long id){
        achievementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
