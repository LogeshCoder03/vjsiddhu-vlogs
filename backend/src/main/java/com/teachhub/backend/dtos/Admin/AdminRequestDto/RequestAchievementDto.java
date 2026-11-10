package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.enums.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAchievementDto {
    private String title;
    private String description;
    private String username;
    private AchievementType type;
    private Integer priority;
}
