package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminClipsDto {
    private Long id;
    private ShootType type;
    private String videoUrl;
    private LocalDateTime createdAt;
    private Boolean isPublished;
    private String username;
}
