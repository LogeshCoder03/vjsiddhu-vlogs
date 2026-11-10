package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminRequestPostDto {
    private String title;
    private String content;
    private String username;
    private Long totalLikes;
}
