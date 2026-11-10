package com.teachhub.backend.dtos.CommentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseCommentDto {
    private Long id;
    private String comment;
    private Long likes;
    private String username;
    private LocalDateTime createdAt;
}
