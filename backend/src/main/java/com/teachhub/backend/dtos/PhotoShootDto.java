package com.teachhub.backend.dtos;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoShootDto {
    private Long id;
    private String imagePath;
    private ShootType type;
}
