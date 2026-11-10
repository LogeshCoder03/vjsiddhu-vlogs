package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.UserFeastDto.RequestUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.ResponseUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.UserFeastWithFeastDto;
import com.teachhub.backend.services.UserFeastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-events")
public class UserFeastController {
    @Autowired
    private UserFeastService userFeastService;

    @PostMapping
    public ResponseEntity<ResponseData> createUserFeast(@RequestBody RequestUserFeastDto userFeastDto,
                                                        @RequestParam(required = true,name = "feast_id") Long feastId,
                                                        Authentication authentication){
        userFeastService.create(userFeastDto,feastId,authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("OTP has been sent successfully"));
    }
    @GetMapping
    public Page<UserFeastWithFeastDto> getUsersFeasts(Authentication authentication,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        return userFeastService.getAll(page,size,authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsersFeast(@PathVariable Long id,Authentication authentication){
        userFeastService.delete(id,authentication.getName());
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseData> verifyData(@RequestParam String otp,@RequestParam Long mobileNumber){
        userFeastService.verifyOtp(mobileNumber,otp);
        return ResponseEntity.ok(new ResponseData("Event registration success."));
    }
}
