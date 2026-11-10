package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminRequestCommentDto {
    private String comment;
    private Long likes;
    private String username;
    private Long postId;
}
