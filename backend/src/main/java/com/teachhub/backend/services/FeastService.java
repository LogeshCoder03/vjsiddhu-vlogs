package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestFeastDto;
import com.teachhub.backend.dtos.FeastResponseDto;
import com.teachhub.backend.dtos.Admin.AdminFeastDto;
import com.teachhub.backend.dtos.FeastDto;
import com.teachhub.backend.entities.Address;
import com.teachhub.backend.entities.Feast;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.AddressMapper;
import com.teachhub.backend.mappers.FeastMapper;
import com.teachhub.backend.repositories.FeastRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FeastService {
    private FeastRepository feastRepository;
    private FeastMapper feastMapper;
    private AddressMapper addressMapper;
    private UserRepository userRepository;

    public FeastDto create(FeastDto feastDto, MultipartFile image,String username) throws IOException {
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        File dir=new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        image.transferTo(new File(filePath));
        Address venue=addressMapper.toEntity(feastDto.getVenue());
        Feast feast=feastMapper.toEntity(feastDto);
        feast.setVenue(venue);
        feast.setLogo(fileName);
        feast.setLogoPath(filePath);
        feast.setUser(user);
        Feast createdFeast=feastRepository.save(feast);
        return feastMapper.toDto(createdFeast);
    }

    public Page<FeastResponseDto> getAll(int pageNumber,int size){
        Pageable pageable=PageRequest.of(pageNumber,size);
        return feastRepository.findByIsLaunchedTrue(pageable)
                .map(feastMapper::toResponseDto);
    }
    public FeastResponseDto get(Long id){
        Feast feast=feastRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new);
        return feastMapper.toResponseDto(feast);
    }
    public void update(Long id,FeastDto feastDto,MultipartFile image,String username)throws IOException{
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Feast feast=feastRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        Address venue=addressMapper.toEntity(feastDto.getVenue());
        feast.setTiming(feastDto.getTiming());
        feast.setName(feastDto.getName());
        feast.setDescription(feastDto.getDescription());
        feast.setTotalMembers(feastDto.getTotalMembers());
        feast.setVenue(venue);
        if (image!=null && !image.isEmpty()){
            String fileName=image.getOriginalFilename();
            String uploadDir=System.getProperty("user.dir")+"/uploads/";
            String filePath=uploadDir+fileName;
            image.transferTo(new File(filePath));
            feast.setLogo(fileName);
            feast.setLogoPath(filePath);
        }
        feastRepository.save(feast);
    }

    public void delete(Long id){
        feastRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        feastRepository.deleteById(id);
    }

    public Page<AdminFeastDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return feastRepository.findAll(pageable)
                .map(feastMapper::toAdminDto);
    }

    public void createByAdmin(RequestFeastDto requestFeastDto,MultipartFile image)throws IOException{
        User user=userRepository.findUserByUsername(requestFeastDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        String fileName=image.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/";
        String filePath=uploadDir+fileName;
        image.transferTo(new File(filePath));
        Address venue=addressMapper.toEntity(requestFeastDto.getVenue());
        Feast feast=feastMapper.toEntity(requestFeastDto);
        if (feast.getIsLaunched()!=null&&feast.getIsLaunched().equals(true)){
            feast.setLaunchedAt(LocalDateTime.now());
        }
        feast.setUser(user);
        feast.setLogo(fileName);
        feast.setLogoPath(filePath);
        feast.setVenue(venue);
        feastRepository.save(feast);
    }

    public void updateByAdmin(Long id,RequestFeastDto requestFeastDto,MultipartFile image)throws IOException{
        Feast oldFeast=feastRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        User user=userRepository.findUserByUsername(requestFeastDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        Address venue=addressMapper.toEntity(requestFeastDto.getVenue());
        Feast feast=feastMapper.toEntity(requestFeastDto);
        if (image!=null && !image.isEmpty()){
            String fileName=image.getOriginalFilename();
            String uploadDir=System.getProperty("user.dir")+"/uploads/";
            String filePath=uploadDir+fileName;
            image.transferTo(new File(filePath));
            feast.setLogo(fileName);
            feast.setLogoPath(filePath);
        }else {
            feast.setLogoPath(oldFeast.getLogoPath());
            feast.setLogo(oldFeast.getLogo());
        }
        if (feast.getIsLaunched().equals(true)){
            feast.setLaunchedAt(LocalDateTime.now());
        }
        feast.setCreatedAt(oldFeast.getCreatedAt());
        feast.setUser(user);
        feast.setVenue(venue);
        feast.setId(id);
        feastRepository.save(feast);
    }
}
