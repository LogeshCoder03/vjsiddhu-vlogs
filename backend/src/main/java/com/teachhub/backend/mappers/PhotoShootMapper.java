package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestPhotoShootDto;
import com.teachhub.backend.dtos.Admin.AdminPhotoShootDto;
import com.teachhub.backend.dtos.PhotoShootDto;
import com.teachhub.backend.entities.PhotoShoots;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoShootMapper {
    PhotoShoots toEntity(PhotoShootDto photoShootDto);
    PhotoShootDto toDto(PhotoShoots photoShoots);
    @Mapping(source = "user.username",target = "username")
    AdminPhotoShootDto toAdminDto(PhotoShoots photoShoots);
    PhotoShoots toEntity(RequestPhotoShootDto requestPhotoShootDto);
}
