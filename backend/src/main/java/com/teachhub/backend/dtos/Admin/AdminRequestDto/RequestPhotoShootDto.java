package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.enums.ShootType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPhotoShootDto {
    private ShootType type;
    private String username;
    private Boolean isPublished;
}
