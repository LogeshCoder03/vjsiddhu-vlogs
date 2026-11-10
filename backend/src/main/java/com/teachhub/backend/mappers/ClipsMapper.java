package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminClipsDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestClipDto;
import com.teachhub.backend.entities.UnseenClips;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClipsMapper {
    @Mapping(source = "user.username",target = "username")

    AdminClipsDto toAdminDto(UnseenClips unseenClips);
    UnseenClips toEntity(AdminRequestClipDto adminRequestClipDto);
}
