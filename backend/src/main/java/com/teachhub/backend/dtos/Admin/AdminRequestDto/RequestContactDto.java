package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestContactDto {
    private String username;
    private String name;
    private String email;
    private String message;
}
