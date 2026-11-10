package com.teachhub.backend.dtos;

import com.teachhub.backend.enums.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AchievementDto {
    private String title;
    private String description;
    private AchievementType type;
    private Integer priority;
}
