package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestPhotoShootDto;
import com.teachhub.backend.dtos.Admin.AdminPhotoShootDto;
import com.teachhub.backend.dtos.PhotoShootDto;
import com.teachhub.backend.entities.PhotoShoots;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.enums.ShootType;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.PhotoShootMapper;
import com.teachhub.backend.repositories.PhotoShootRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PhotoShootService {
    private UserRepository userRepository;
    private PhotoShootRepository photoShootRepository;
    private PhotoShootMapper photoShootMapper;

    public Page<PhotoShootDto> getAll(int pageNumber,int size){
        Pageable pageable= PageRequest.of(pageNumber,size);
        return photoShootRepository.findPhotoShootsByIsPublished(true,pageable)
                .map(photoShootMapper::toDto);
    }
    public void create(MultipartFile image, ShootType type, String username)throws IOException {
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        image.transferTo(new File(filePath));
        PhotoShoots photoShoots=new PhotoShoots();
        photoShoots.setUser(user);
        photoShoots.setType(type);
        photoShoots.setFileName(fileName);
        photoShoots.setImagePath(filePath);
        photoShootRepository.save(photoShoots);
    }
    public void delete(Long id){
        photoShootRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        photoShootRepository.deleteById(id);
    }
    public void publish(Long id){
        PhotoShoots photoShoots=photoShootRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        photoShoots.setIsPublished(true);
        photoShootRepository.save(photoShoots);
    }
    public void unPublish(Long id){
        PhotoShoots photoShoots=photoShootRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        photoShoots.setIsPublished(false);
        photoShootRepository.save(photoShoots);
    }
    public Page<AdminPhotoShootDto> getAllForAdmin(int pageNumber){
        Pageable pageable=PageRequest.of(pageNumber,10);
        return photoShootRepository.findAll(pageable)
                .map(photoShootMapper::toAdminDto);
    }
    public void adminCreate(RequestPhotoShootDto requestPhotoShootDto,MultipartFile image)throws IOException{
        User user=userRepository.findUserByUsername(requestPhotoShootDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        image.transferTo(new File(filePath));
        PhotoShoots photoShoots=photoShootMapper.toEntity(requestPhotoShootDto);
        photoShoots.setUser(user);
        photoShoots.setFileName(fileName);
        photoShoots.setImagePath(filePath);
        photoShootRepository.save(photoShoots);
    }
}
