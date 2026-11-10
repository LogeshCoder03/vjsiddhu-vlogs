package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminEventDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestEventDto;
import com.teachhub.backend.entities.Event;
import com.teachhub.backend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bookings")
public class AdminEventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Page<AdminEventDto>> getAllEvents(@RequestParam(defaultValue = "0") int page){
        Page<AdminEventDto> events=eventService.getAllForAdmins(page);
        return ResponseEntity.ok().body(events);
    }
    @PostMapping
    public ResponseEntity<ResponseData> createEvent(@RequestBody AdminRequestEventDto adminRequestEventDto){
        eventService.createByAdmin(adminRequestEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Booking created Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateEvent(@PathVariable Long id,
            @RequestBody AdminRequestEventDto adminRequestEventDto){
        eventService.updateByAdmin(id,adminRequestEventDto);
        return ResponseEntity.ok(new ResponseData("Booking updated successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id){
        eventService.deleteByAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
