package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminClipsDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestClipDto;
import com.teachhub.backend.dtos.UnseenClipsDto;
import com.teachhub.backend.entities.UnseenClips;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.enums.ShootType;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.ClipsMapper;
import com.teachhub.backend.repositories.UnseenClipsRepository;
import com.teachhub.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UnseenClipService {
    private UserRepository userRepository;
    private UnseenClipsRepository unseenClipsRepository;
    private ClipsMapper clipsMapper;

    public Page<UnseenClipsDto> getAll(int pageNumber,int size){
        Pageable pageable= PageRequest.of(pageNumber,size, Sort.by("createdAt").ascending());
        return unseenClipsRepository.findUnseenClipsByIsPublished(true,pageable)
                .map(clip->{
                    UnseenClipsDto unseenClipsDto=new UnseenClipsDto();
                    unseenClipsDto.setVideoUrl("http://localhost:8080/uploads/"+clip.getFileName());
                    unseenClipsDto.setId(clip.getId());
                    unseenClipsDto.setType(clip.getType());
                    return unseenClipsDto;
                });
    }
    public void create(MultipartFile video, ShootType type, String username)throws IOException {
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        String fileName=video.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/unseen_clips";
        String filePath=uploadDir+fileName;
        video.transferTo(new File(filePath));
        UnseenClips unseenClips=new UnseenClips();
        unseenClips.setUser(user);
        unseenClips.setType(type);
        unseenClips.setFileName(fileName);
        unseenClips.setVideoPath(filePath);
        unseenClipsRepository.save(unseenClips);
    }
    public UnseenClips getOne(Long id){
        return unseenClipsRepository.findUnseenClipsByIsPublishedAndId(true,id).orElseThrow(
                ResourceNotFoundException::new
        );
    }
    public void delete(Long id){
        unseenClipsRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        unseenClipsRepository.deleteById(id);
    }
    public void publish(Long id){
        UnseenClips unseenClips=unseenClipsRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        unseenClips.setIsPublished(true);
        unseenClipsRepository.save(unseenClips);
    }
    public void unPublish(Long id){
        UnseenClips unseenClips=unseenClipsRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        unseenClips.setIsPublished(false);
        unseenClipsRepository.save(unseenClips);
    }
    public Page<AdminClipsDto> getAllForAdmin(int pageNumber){
        Pageable pageable=PageRequest.of(pageNumber,10);
        return unseenClipsRepository.findAll(pageable)
                .map(clip->{
                    AdminClipsDto adminClipsDto=clipsMapper.toAdminDto(clip);
                    adminClipsDto.setVideoUrl("http://localhost:8080/uploads/"+clip.getFileName());
                    return adminClipsDto;
                });
    }
    public void createForAdmin(MultipartFile video,AdminRequestClipDto adminRequestClipDto)throws IOException{
        User user=userRepository.findUserByUsername(adminRequestClipDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        String fileName=video.getOriginalFilename();
        String uploadDir=System.getProperty("user.dir")+"/uploads/unseen_clips";
        String filePath=uploadDir+fileName;
        video.transferTo(new File(filePath));
        UnseenClips unseenClips=clipsMapper.toEntity(adminRequestClipDto);
        unseenClips.setVideoPath(filePath);
        unseenClips.setFileName(fileName);
        unseenClips.setUser(user);
        unseenClipsRepository.save(unseenClips);
    }
}
