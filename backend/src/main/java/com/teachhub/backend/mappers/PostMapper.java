package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminPostDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestPostDto;
import com.teachhub.backend.dtos.PostsDto.RequestPostDto;
import com.teachhub.backend.dtos.PostsDto.ResponsePostDto;
import com.teachhub.backend.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(RequestPostDto requestPostDto);
    ResponsePostDto toDto(Post posts);
    @Mapping(source = "user.username",target = "username")
    AdminPostDto toAdminDto(Post post);
    Post toEntity(AdminRequestPostDto requestPostDto);
}
