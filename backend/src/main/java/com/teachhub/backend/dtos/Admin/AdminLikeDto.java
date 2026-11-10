package com.teachhub.backend.dtos.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminLikeDto {
    private Long id;
    private Long postId;
    private Long commentId;
    private String username;
    private LocalDateTime createdAt;
}
