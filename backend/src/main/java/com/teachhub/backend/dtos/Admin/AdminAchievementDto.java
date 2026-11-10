package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.enums.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAchievementDto {
    private Long id;
    private String title;
    private String description;
    private String username;
    private LocalDateTime createdAt;
    private AchievementType type;
    private Integer priority;
}
