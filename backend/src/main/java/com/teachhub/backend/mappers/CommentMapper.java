package com.teachhub.backend.mappers;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestCommentDto;
import com.teachhub.backend.dtos.CommentDtos.RequestCommentDto;
import com.teachhub.backend.dtos.CommentDtos.ResponseCommentDto;
import com.teachhub.backend.dtos.Admin.AdminCommentDto;
import com.teachhub.backend.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(RequestCommentDto requestCommentDto);
    @Mapping(source = "user.username",target = "username")
    ResponseCommentDto toDto(Comment comment);
    Comment toEntity(AdminRequestCommentDto adminRequestCommentDto);
    @Mapping(source = "user.username",target = "username")
    @Mapping(source = "post.id",target = "postId")
    AdminCommentDto toAdminDto(Comment comment);
}
