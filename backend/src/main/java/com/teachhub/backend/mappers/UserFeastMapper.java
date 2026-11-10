package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserFeastDto;
import com.teachhub.backend.dtos.Admin.AdminUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.RequestUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.ResponseUserFeastDto;
import com.teachhub.backend.dtos.UserFeastDto.UserFeastWithFeastDto;
import com.teachhub.backend.entities.UserFeast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserFeastMapper {
    ResponseUserFeastDto toDto(UserFeast userFeast);
    UserFeast toEntity(RequestUserFeastDto userFeastDto);
    UserFeast toEntity(AdminRequestUserFeastDto adminRequestUserFeastDto);
    @Mapping(source = "feast.logoPath",target = "eventLogo")
    @Mapping(source = "feast.name",target = "eventName")
    @Mapping(source = "feast.status",target = "eventStatus")
    @Mapping(source = "feast.timing",target = "eventTiming")
    UserFeastWithFeastDto toGetAll(UserFeast userFeast);
    @Mapping(source = "feast.id",target = "feastId")
    @Mapping(source = "user.username",target = "username")
    AdminUserFeastDto toAdminDto(UserFeast userFeast);
}
