package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminEventDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserFeastDto;
import com.teachhub.backend.dtos.Admin.AdminUserFeastDto;
import com.teachhub.backend.entities.Event;
import com.teachhub.backend.entities.UserFeast;
import com.teachhub.backend.services.EventService;
import com.teachhub.backend.services.UserFeastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user-events")
public class AdminUserFeastController {
    @Autowired
    private UserFeastService userFeastService;

    @GetMapping
    public ResponseEntity<Page<AdminUserFeastDto>> getAllUserFeasts(@RequestParam(defaultValue = "0") int page){
        Page<AdminUserFeastDto> userFeasts=userFeastService.getAllForAdmin(page);
        return ResponseEntity.ok().body(userFeasts);
    }
    @PostMapping
    public ResponseEntity<ResponseData> createUserFeasts(@RequestBody AdminRequestUserFeastDto adminRequestUserFeastDto){
        userFeastService.createByAdmin(adminRequestUserFeastDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("User Event created Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateUserFeasts(@RequestBody AdminRequestUserFeastDto adminRequestUserFeastDto,
                                                  @PathVariable Long id){
        userFeastService.updateByAdmin(id,adminRequestUserFeastDto);
        return ResponseEntity.ok(new ResponseData("User Event updated Successfully"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserFeasts(@PathVariable Long id){
        userFeastService.deleteByAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
