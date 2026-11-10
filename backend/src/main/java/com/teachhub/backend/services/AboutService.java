package com.teachhub.backend.services;

import com.teachhub.backend.dtos.AboutDto;
import com.teachhub.backend.dtos.RequestAboutDto;
import com.teachhub.backend.entities.About;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.AboutMapper;
import com.teachhub.backend.repositories.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AboutService {
    @Autowired
    private AboutRepository aboutRepository;
    @Autowired
    private AboutMapper aboutMapper;

    public void create(RequestAboutDto requestAboutDto,
                       MultipartFile image)throws IOException {
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        image.transferTo(new File(filePath));
        About about=aboutMapper.toEntity(requestAboutDto);
        about.setImagePath(fileName);
        about.setImage(filePath);
        aboutRepository.save(about);
    }
    public List<AboutDto> getAll(){
        return aboutRepository.findAll()
                .stream()
                .map(aboutMapper::toDto)
                .toList();
    }
    public void update(Long id,RequestAboutDto requestAboutDto,MultipartFile image)throws IOException{
        aboutRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        About about=aboutMapper.toEntity(requestAboutDto);
        if (image!=null) {
            String fileName=image.getOriginalFilename();
            String uploadDir=System.getProperty("user.dir")+"/uploads/";
            String filePath=uploadDir+fileName;
            image.transferTo(new File(filePath));
            about.setImagePath(filePath);
            about.setImage(fileName);
        }
        aboutRepository.save(about);
    }
    public void delete(Long id){
        aboutRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        aboutRepository.deleteById(id);
    }
}
