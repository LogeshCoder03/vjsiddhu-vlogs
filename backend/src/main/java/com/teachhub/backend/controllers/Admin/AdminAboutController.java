package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.AboutDto;
import com.teachhub.backend.dtos.RequestAboutDto;
import com.teachhub.backend.services.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/about")
public class AdminAboutController {
    @Autowired
    private AboutService aboutService;

    @GetMapping
    public List<AboutDto> getAbouts(){
        return aboutService.getAll();
    }

    @PostMapping
    private ResponseEntity<ResponseData> createAbout(@ModelAttribute RequestAboutDto requestAboutDto,
                                                     @RequestParam MultipartFile image){
        try{
            aboutService.create(requestAboutDto,image);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("About of the member is created"));
        }catch (IOException exception){
            throw new RuntimeException("Error occurs");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateAbout(@PathVariable Long id,
                                                    @ModelAttribute RequestAboutDto requestAboutDto,
                                                    @RequestParam MultipartFile image){
        try{
            aboutService.update(id,requestAboutDto,image);
            return ResponseEntity.ok(new ResponseData());
        }catch (IOException exception){
            throw new RuntimeException("Error occurs");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAbout(@PathVariable Long id){
        aboutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
