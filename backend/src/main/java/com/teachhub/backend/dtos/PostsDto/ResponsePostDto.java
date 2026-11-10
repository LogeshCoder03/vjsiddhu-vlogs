package com.teachhub.backend.dtos.PostsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponsePostDto {
    private Long id;
    private String title;
    private String content;
    private Long totalLikes;
    private LocalDateTime createdAt;
}
