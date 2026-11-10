package com.teachhub.backend.dtos.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminPostDto {
    private Long id;
    private String title;
    private String content;
    private Long totalLikes;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
}
