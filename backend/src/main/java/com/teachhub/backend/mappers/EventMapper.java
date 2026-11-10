package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.Admin.AdminEventDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestEventDto;
import com.teachhub.backend.dtos.EventDto.RequestEventDto;
import com.teachhub.backend.dtos.EventDto.ResponseEventDto;
import com.teachhub.backend.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    ResponseEventDto toDto(Event event);
    Event toEntity(RequestEventDto eventDto);
    Event toEntity(AdminRequestEventDto adminRequestEventDto);
    @Mapping(source = "user.username",target = "username")
    AdminEventDto toAdminDto(Event event);
}
