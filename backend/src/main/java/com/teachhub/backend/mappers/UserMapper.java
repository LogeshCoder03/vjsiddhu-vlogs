package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserDto;
import com.teachhub.backend.dtos.Admin.AdminUserDto;
import com.teachhub.backend.dtos.UserDtos.LoginRequestDto;
import com.teachhub.backend.dtos.UserDtos.UserRegisterDto;
import com.teachhub.backend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerDtoToEntity(UserRegisterDto userRegisterDto);
    User loginDtoToEntity(LoginRequestDto loginRequestDto);
    User toEntity(AdminRequestUserDto adminUserDto);
    AdminUserDto toDto(User user);
}
