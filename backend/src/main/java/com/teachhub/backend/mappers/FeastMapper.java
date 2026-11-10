package com.teachhub.backend.mappers;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestFeastDto;
import com.teachhub.backend.dtos.FeastResponseDto;
import com.teachhub.backend.dtos.Admin.AdminFeastDto;
import com.teachhub.backend.dtos.FeastDto;
import com.teachhub.backend.entities.Feast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeastMapper {
    FeastDto toDto(Feast feast);
    FeastResponseDto toResponseDto(Feast feast);
    Feast toEntity(FeastDto feastDto);
    @Mapping(source = "user.username",target = "username")
    AdminFeastDto toAdminDto(Feast feast);
    Feast toEntity(RequestFeastDto requestFeastDto);
}
