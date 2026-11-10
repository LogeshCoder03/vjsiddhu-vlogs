package com.teachhub.backend.dtos;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnseenClipsDto {
    private Long id;
    private String videoUrl;
    private ShootType type;
}
