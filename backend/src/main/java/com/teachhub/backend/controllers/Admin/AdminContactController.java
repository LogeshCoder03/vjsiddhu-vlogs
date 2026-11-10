package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminContactDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestContactDto;
import com.teachhub.backend.entities.Contact;
import com.teachhub.backend.mappers.ContactMapper;
import com.teachhub.backend.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/contacts")
public class AdminContactController {
    private ContactService contactService;
    private ContactMapper contactMapper;

    @GetMapping
    public Page<AdminContactDto> getAllContacts(@RequestParam(defaultValue = "0") int page){
        return contactService.getAllForAdmin(page);
    }

    @PostMapping
    public ResponseEntity<ResponseData> createContact(@RequestBody RequestContactDto requestContactDto){
        contactService.createForAdmin(requestContactDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Contact created Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateContact(@RequestBody RequestContactDto requestContactDto,
                                                    @PathVariable Long id){
        contactService.updateByAdmin(id,requestContactDto);
        return ResponseEntity.ok(new ResponseData("Contact updated successfully."));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> createContacts(@PathVariable Long id){
        contactService.deleteByAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
