package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.FeastDto;
import com.teachhub.backend.dtos.FeastResponseDto;
import com.teachhub.backend.services.FeastService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/feasts")
public class FeastController {
    @Autowired
    private FeastService feastService;

    @GetMapping
    public ResponseEntity<Page<FeastResponseDto>> getFeasts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<FeastResponseDto> feasts=feastService.getAll(page,size);
        return ResponseEntity.ok().body(feasts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeastResponseDto> getFeast(@PathVariable Long id){
        FeastResponseDto feast=feastService.get(id);
        return ResponseEntity.ok(feast);
    }

    @PostMapping
    public ResponseEntity<ResponseData> createFeast(@Valid @ModelAttribute FeastDto feastDto,
                                @RequestParam("logo") MultipartFile logo, Authentication authentication){
        try {
            feastService.create(feastDto,logo,authentication.getName());
            return ResponseEntity.ok(new ResponseData("Event created successfully"));
        }catch (IOException e){
            throw new RuntimeException("Error occurs");

        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateFeast(@PathVariable Long id,
                                                    @Valid @ModelAttribute FeastDto feastDto,
                                                    @RequestParam(value = "logo",required = false) MultipartFile logo,
                                                    Authentication authentication){
        try {
            feastService.update(id,feastDto,logo, authentication.getName());
            return ResponseEntity.ok(new ResponseData("Event updated successfully"));
        }catch (IOException e){
            throw new RuntimeException("Error occurs");
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFeast(@PathVariable Long id){
        feastService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
