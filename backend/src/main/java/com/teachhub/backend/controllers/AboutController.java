package com.teachhub.backend.controllers;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.AboutDto;
import com.teachhub.backend.dtos.RequestAboutDto;
import com.teachhub.backend.services.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {
    @Autowired
    private AboutService aboutService;

    @GetMapping
    public List<AboutDto> getAbouts(){
        return aboutService.getAll();
    }

}
