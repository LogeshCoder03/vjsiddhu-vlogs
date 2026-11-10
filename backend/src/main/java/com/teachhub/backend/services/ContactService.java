package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminContactDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestContactDto;
import com.teachhub.backend.dtos.ContactDto;
import com.teachhub.backend.entities.Contact;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.ContactMapper;
import com.teachhub.backend.repositories.ContactRepository;
import com.teachhub.backend.repositories.UserRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private UserRepository userRepository;

    public void create(ContactDto data,@Nullable String username){
        Contact contactData=contactMapper.toEntity(data);
        if (username!=null){
            User user=userRepository.findUserByUsername(username).orElseThrow(
                    ()->new RuntimeException("User not found")
            );
            contactData.setUser(user);
        }
        contactRepository.save(contactData);
    }

    public Page<AdminContactDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return contactRepository.findAll(pageable)
                .map(contactMapper::toAdminDto);
    }

    public void createForAdmin(RequestContactDto requestContactDto){
        User user=userRepository.findUserByUsername(requestContactDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Contact contactData=contactMapper.toEntity(requestContactDto);
        contactData.setUser(user);
        contactRepository.save(contactData);
    }

    public void updateByAdmin(Long id,RequestContactDto requestContactDto){
        User user=userRepository.findUserByUsername(requestContactDto.getUsername()).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        contactRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        Contact contactData=contactMapper.toEntity(requestContactDto);
        contactData.setId(id);
        contactData.setUser(user);
        contactRepository.save(contactData);
    }

    public void deleteByAdmin(Long id){
        contactRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        contactRepository.deleteById(id);
    }
}
