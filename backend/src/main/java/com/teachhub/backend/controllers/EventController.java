package com.teachhub.backend.controllers;
import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.EventDto.RequestEventDto;
import com.teachhub.backend.dtos.EventDto.ResponseEventDto;
import com.teachhub.backend.enums.EventType;
import com.teachhub.backend.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/events")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<ResponseData> createEvent(
            @RequestParam(required = true,name = "event_type") EventType eventType,
             @RequestBody RequestEventDto eventDto,
            Authentication authentication){
        System.out.println(eventDto);
        eventService.create(eventType,eventDto,authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Booking registered successfully."));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseEventDto>> getUserEvents(Authentication authentication,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        Page<ResponseEventDto> events=eventService.getAll(page,size,authentication.getName());
        return ResponseEntity.ok().body(events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateEvent(@PathVariable Long id,
                                              @Valid @RequestBody RequestEventDto eventDto,
                                                          Authentication authentication){
        eventService.update(id,eventDto,authentication.getName());
        return ResponseEntity.ok(new ResponseData("Booking registration updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id,Authentication authentication){
        eventService.delete(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
