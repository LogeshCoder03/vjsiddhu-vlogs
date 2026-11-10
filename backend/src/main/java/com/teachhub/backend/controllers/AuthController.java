package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.UserDtos.LoginRequestDto;
import com.teachhub.backend.dtos.UserDtos.UserRegisterDto;
import com.teachhub.backend.entities.Role;
import com.teachhub.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> createUser(@Valid @RequestBody UserRegisterDto userRegisterDto){
        try{
            userService.register(userRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Registration successful! A verification email has been sent to your inbox.Please check your email and verify your account"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseData> userLogin(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ){
        String token=userService.login(loginRequestDto);
        if (token==null){
            return ResponseEntity.badRequest().build();
        }
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60*60+60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(new ResponseData("Login successful."));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication){
        if (authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.ok().body(new ResponseData("We couldn’t find this user profile."));
        }
        UserDetails user=(UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(Map.of("username",user.getUsername(),"roles",user.getAuthorities()));
    }


    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(HttpServletResponse response){
        Cookie cookie=new Cookie("token",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(new ResponseData("Logged out successfully."));
    }

    @GetMapping("/verify")
    public ResponseEntity<ResponseData> verifyMail(@RequestParam String token){
        ResponseData responseData=userService.verifyUser(token);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseData> forgotPassword(@RequestBody String email){
        try {
            ResponseData responseData=userService.forgotPassword(email);
            return ResponseEntity.ok(responseData);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseData> resetPassword(@RequestParam String token,
                                                      @RequestBody String password){
        ResponseData responseData=userService.resetPassword(token,password);
        return ResponseEntity.ok(responseData);
    }
}
