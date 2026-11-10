package com.teachhub.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestAboutDto {
    private String name;
    private String about;
    private String role;
    private String instagram;
}
