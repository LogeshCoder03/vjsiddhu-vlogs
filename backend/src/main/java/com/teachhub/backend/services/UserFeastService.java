package com.teachhub.backend.services;

import com.teachhub.backend.Utils.KeyUtil;
import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserFeastDto;
import com.teachhub.backend.dtos.Admin.AdminUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.RequestUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.ResponseUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.UserFeastWithFeastDto;
import com.teachhub.backend.entities.Feast;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.entities.UserFeast;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.UserFeastMapper;
import com.teachhub.backend.repositories.FeastRepository;
import com.teachhub.backend.repositories.UserFeastRepository;
import com.teachhub.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserFeastService {
    @Autowired
    private UserFeastRepository userFeastRepository;

    @Autowired
    private UserFeastMapper userFeastMapper;

    @Autowired
    private  FeastRepository feastRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseUserFeastDto create(RequestUserFeastDto userFeastDto,Long feastId,String username) {
        Feast feast=feastRepository.findById(feastId).orElseThrow(()->
            new RuntimeException("Event not found")
        );
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Boolean isExists=userFeastRepository.existsUserFeastByUserAndFeast(user,feast);
        if (isExists){
            throw new RuntimeException("User already registered");
        }
        UserFeast createdUserfeast=userFeastMapper.toEntity(userFeastDto);
        String otp=String.valueOf(new Random().nextInt(900000)+100000);
        createdUserfeast.setOtp(otp);
        createdUserfeast.setOtpExpires(LocalDateTime.now().plusMinutes(5));
        createdUserfeast.setFeast(feast);
        createdUserfeast.setUser(user);
        String token= KeyUtil.generateToken();
        createdUserfeast.setToken(token);
        UserFeast userFeast=userFeastRepository.save(createdUserfeast);
        return userFeastMapper.toDto(userFeast);
    }

    public Page<UserFeastWithFeastDto> getAll(int page,int size,String username){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Pageable pageable=PageRequest.of(page,size);
        return userFeastRepository.findUserFeastByUser(user,pageable)
                .map(userFeastMapper::toGetAll);
    }

    public void delete(Long id,String username){
        userFeastRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Your not register for the feast")
        );
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        userFeastRepository.deleteUserFeastByUserAndId(user,id);
    }

    public Page<AdminUserFeastDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return userFeastRepository.findAll(pageable)
                .map(userFeastMapper::toAdminDto);
    }
    public void deleteByAdmin(Long id){
        userFeastRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        userFeastRepository.deleteById(id);
    }
    public void updateByAdmin(Long id,AdminRequestUserFeastDto adminRequestUserFeastDto){
        userFeastRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        User user=userRepository.findUserByUsername(adminRequestUserFeastDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        Feast feast=feastRepository.findById(adminRequestUserFeastDto.getFeastId())
                .orElseThrow(()->new RuntimeException("Feast not found"));
        UserFeast userFeast=userFeastMapper.toEntity(adminRequestUserFeastDto);
        userFeast.setUser(user);
        userFeast.setFeast(feast);
        userFeast.setId(id);
        userFeastRepository.save(userFeast);
    }
    public void createByAdmin(AdminRequestUserFeastDto adminRequestUserFeastDto){
        User user=userRepository.findUserByUsername(adminRequestUserFeastDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        Feast feast=feastRepository.findById(adminRequestUserFeastDto.getFeastId())
                .orElseThrow(()->new RuntimeException("Feast not found"));
        UserFeast userFeast=userFeastMapper.toEntity(adminRequestUserFeastDto);
        userFeast.setUser(user);
        userFeast.setFeast(feast);
        userFeastRepository.save(userFeast);
    }

    public void verifyOtp(Long mobileNumber,String otp) {
        UserFeast userFeast = userFeastRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(ResourceNotFoundException::new);
        if (!userFeast.getOtp().equals(otp)) {
            throw new RuntimeException();
        }
        if (userFeast.getOtpExpires().isBefore(LocalDateTime.now())) {
            throw new RuntimeException();
        }
        userFeast.setIsVerified(true);
        userFeastRepository.save(userFeast);
    }
}
