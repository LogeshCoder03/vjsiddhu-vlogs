package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminLikeDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestLikeDto;
import com.teachhub.backend.entities.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "comment.id",target = "commentId")
    @Mapping(source = "post.id",target = "postId")
    @Mapping(source = "user.username",target = "username")
    AdminLikeDto toDto(Like like);
}
