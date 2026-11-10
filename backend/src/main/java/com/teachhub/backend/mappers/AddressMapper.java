package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.entities.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);
    AddressDto toDto(Address address);
}
