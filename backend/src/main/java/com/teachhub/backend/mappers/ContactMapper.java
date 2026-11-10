package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminContactDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestContactDto;
import com.teachhub.backend.dtos.ContactDto;
import com.teachhub.backend.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toEntity(ContactDto data);
    ContactDto toDto(Contact data);
    Contact toEntity(RequestContactDto adminContactDto);
    @Mapping(source = "user.username",target = "username")
    AdminContactDto toAdminDto(Contact contact);
}
