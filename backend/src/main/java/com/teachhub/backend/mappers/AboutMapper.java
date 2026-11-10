package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.AboutDto;
import com.teachhub.backend.dtos.RequestAboutDto;
import com.teachhub.backend.entities.About;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AboutMapper {
    About toEntity(RequestAboutDto requestAboutDto);
    AboutDto toDto(About about);
}
