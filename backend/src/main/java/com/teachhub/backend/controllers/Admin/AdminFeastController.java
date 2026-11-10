package com.teachhub.backend.controllers.Admin;


import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminFeastDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestFeastDto;
import com.teachhub.backend.services.FeastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminFeastController {
    @Autowired
    private FeastService feastService;

    @GetMapping
    public ResponseEntity<Page<AdminFeastDto>> getAllFeasts(@RequestParam(defaultValue = "0") int page){
        Page<AdminFeastDto> feasts=feastService.getAllForAdmin(page);
        return ResponseEntity.ok().body(feasts);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createFeast(@RequestPart("data") RequestFeastDto requestFeastDto,
                                         @RequestPart("image") MultipartFile image) {

        System.out.println(requestFeastDto.toString());
        try {
            feastService.createByAdmin(requestFeastDto, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Event created Successfully"));
        }catch (IOException exception){
           throw new RuntimeException("Error occurs");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateFeast(@PathVariable Long id,
                                                  @RequestPart("data") RequestFeastDto requestFeastDto,
                                                  @RequestPart(value = "image",required = false) MultipartFile image)throws IOException{
        feastService.updateByAdmin(id,requestFeastDto,image);
        return ResponseEntity.ok(new ResponseData("Event created Successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFeast(@PathVariable Long id){
        feastService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
