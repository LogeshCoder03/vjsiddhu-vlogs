package com.teachhub.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AboutDto {
    private Long id;
    private String name;
    private String about;
    private String role;
    private String instagram;
    private LocalDateTime createdAt;
}
