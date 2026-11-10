package com.teachhub.backend.services;

import com.teachhub.backend.dtos.AchievementDto;
import com.teachhub.backend.dtos.Admin.AdminAchievementDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestAchievementDto;
import com.teachhub.backend.entities.Achievements;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.AchievementMapper;
import com.teachhub.backend.repositories.AchievementRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AchievementService {
    private AchievementRepository achievementRepository;
    private UserRepository userRepository;
    private AchievementMapper achievementMapper;

    public void create(AchievementDto achievementDto, String username){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Achievements achievement=achievementMapper.toEntity(achievementDto);
        achievement.setUser(user);
        achievementRepository.save(achievement);
    }

    public List<AchievementDto> getAll(){
        Pageable pageable= PageRequest.of(0,5);
        return achievementRepository.findAll(pageable)
                .stream()
                .map(achievementMapper::toDto)
                .toList();
    }

    public void delete(Long id){
        achievementRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        achievementRepository.deleteById(id);
    }

    public Page<AdminAchievementDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,1);
        return achievementRepository.findAll(pageable)
                .map(achievementMapper::toAdminDto);
    }

    public void adminCreation(RequestAchievementDto requestAchievementDto){
        User user=userRepository.findUserByUsername(requestAchievementDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Achievements achievements=achievementMapper.toEntity(requestAchievementDto);
        achievements.setUser(user);
        achievementRepository.save(achievements);
    }
    public void update(Long id,RequestAchievementDto requestAchievementDto){
        User user=userRepository.findUserByUsername(requestAchievementDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        achievementRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        Achievements achievements=achievementMapper.toEntity(requestAchievementDto);
        achievements.setId(id);
        achievements.setUser(user);
        achievementRepository.save(achievements);
    }
}
