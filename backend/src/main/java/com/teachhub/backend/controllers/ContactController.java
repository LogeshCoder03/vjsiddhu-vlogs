package com.teachhub.backend.controllers;
import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.ContactDto;
import com.teachhub.backend.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/contact")
    public ResponseEntity<ResponseData> addContact(@Valid @RequestBody ContactDto data, Authentication authentication){
        if (authentication==null || !authentication.isAuthenticated()){
            contactService.create(data,null);
        }else {
            contactService.create(data,authentication.getName());
        }
        return new ResponseEntity<>(new ResponseData("Your message has been sent successfully .We will contact you soon"),HttpStatus.CREATED);
    }
}
