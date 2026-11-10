package com.teachhub.backend.dtos.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminCommentDto {
    private Long id;
    private String comment;
    private Long likes;
    private String username;
    private LocalDateTime createdAt;
    private Long postId;
}
