package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminFeastDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserDto;
import com.teachhub.backend.dtos.Admin.AdminUserDto;
import com.teachhub.backend.entities.Feast;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.services.FeastService;
import com.teachhub.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<AdminUserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page){
        Page<AdminUserDto> users=userService.getAllForAdmin(page);
        return ResponseEntity.ok().body(users);
    }
    @PostMapping
    public ResponseEntity<ResponseData> createUser(@RequestBody AdminRequestUserDto adminRequestUserDto){
        userService.createForAdmin(adminRequestUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("User created Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateUser(@RequestBody AdminRequestUserDto adminRequestUserDto,
                                                  @PathVariable Long id){
        userService.updateForAdmin(id,adminRequestUserDto);
        return ResponseEntity.ok(new ResponseData("User updated Successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteForAdmin(id);
        return ResponseEntity.noContent().build();
    }

}
