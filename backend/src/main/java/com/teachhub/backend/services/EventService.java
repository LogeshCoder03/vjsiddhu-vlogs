package com.teachhub.backend.services;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.dtos.Admin.AdminEventDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestEventDto;
import com.teachhub.backend.dtos.EventDto.RequestEventDto;
import com.teachhub.backend.dtos.EventDto.ResponseEventDto;
import com.teachhub.backend.entities.Address;
import com.teachhub.backend.entities.Event;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.enums.EventType;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.mappers.AddressMapper;
import com.teachhub.backend.mappers.EventMapper;
import com.teachhub.backend.repositories.EventRepository;
import com.teachhub.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserRepository userRepository;

    public ResponseEventDto create(EventType eventType, RequestEventDto eventDto,String username){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Event createEvent=eventMapper.toEntity(eventDto);
        Address address=addressMapper.toEntity(eventDto.getAddress());
        createEvent.setAddress(address);
        createEvent.setEventType(eventType);
        createEvent.setUser(user);
        Event event=eventRepository.save(createEvent);
        AddressDto addressDto=addressMapper.toDto(event.getAddress());
        ResponseEventDto responseEventDto=eventMapper.toDto(event);
        responseEventDto.setAddress(addressDto);
        return responseEventDto;
    }
    public Page<ResponseEventDto> getAll(int page,int size,String username){
        Pageable pageable=PageRequest.of(page,size);
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        return eventRepository.findEventByUser(user,pageable)
                .map(event -> {
                    AddressDto addressDto=addressMapper.toDto(event.getAddress());
                    ResponseEventDto eventDto=eventMapper.toDto(event);
                    eventDto.setAddress(addressDto);
                    return eventDto;
                });
    }

    public void update(Long id,RequestEventDto eventDto,String username){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        Event event=eventRepository.findEventByUserAndId(user,id).orElseThrow(
                ResourceNotFoundException::new
        );
        event.setMobileNumber(eventDto.getMobileNumber());
        event.setHeldOn(eventDto.getHeldOn());
        Address address=addressMapper.toEntity(eventDto.getAddress());
        event.setAddress(address);
        eventRepository.save(event);
    }

    public void delete(Long id,String username){
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        eventRepository.findEventByUserAndId(user,id)
                .orElseThrow(ResourceNotFoundException::new);
        eventRepository.deleteById(id);
    }

    public Page<AdminEventDto> getAllForAdmins(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return eventRepository.findAll(pageable)
                .map(eventMapper::toAdminDto);
    }
    public void createByAdmin(AdminRequestEventDto adminRequestEventDto){
        User user=userRepository.findUserByUsername(adminRequestEventDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        Address address=addressMapper.toEntity(adminRequestEventDto.getAddress());
        Event event=eventMapper.toEntity(adminRequestEventDto);
        if (event.getIsAccepted().equals(true)){
            event.setAcceptedAt(LocalDateTime.now());
        }
        event.setAddress(address);
        event.setUser(user);
        eventRepository.save(event);
    }
    public void deleteByAdmin(Long id){
        eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        eventRepository.deleteById(id);
    }
    public void updateByAdmin(Long id,AdminRequestEventDto adminRequestEventDto){
        eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new
                );
        User user=userRepository.findUserByUsername(adminRequestEventDto.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        Address address=addressMapper.toEntity(adminRequestEventDto.getAddress());
        Event event=eventMapper.toEntity(adminRequestEventDto);
        if (event.getIsAccepted().equals(true)){
            event.setAcceptedAt(LocalDateTime.now());
        }
        event.setAddress(address);
        event.setUser(user);
        event.setId(id);
        eventRepository.save(event);
    }
}
