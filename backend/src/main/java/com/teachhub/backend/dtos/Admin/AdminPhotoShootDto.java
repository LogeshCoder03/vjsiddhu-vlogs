package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminPhotoShootDto {
    private Long id;
    private String imagePath;
    private ShootType type;
    private LocalDateTime createdAt;
    private Boolean isPublished;
    private String username;
}
