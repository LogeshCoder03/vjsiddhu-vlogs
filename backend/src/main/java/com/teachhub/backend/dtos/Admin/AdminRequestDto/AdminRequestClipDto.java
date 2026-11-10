package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestClipDto {
    private ShootType type;
    private Boolean isPublished;
    private String username;
}
