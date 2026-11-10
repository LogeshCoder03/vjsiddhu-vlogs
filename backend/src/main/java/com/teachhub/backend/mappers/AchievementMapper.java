package com.teachhub.backend.mappers;

import com.teachhub.backend.dtos.AchievementDto;
import com.teachhub.backend.dtos.Admin.AdminAchievementDto;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestAchievementDto;
import com.teachhub.backend.entities.Achievements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AchievementMapper {
    Achievements toEntity(AchievementDto achievementDto);
    AchievementDto toDto(Achievements Achievements);
    @Mapping(source = "user.username",target = "username")
    AdminAchievementDto toAdminDto(Achievements achievements);
    Achievements toEntity(RequestAchievementDto requestAchievementDto);
}
