package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestLikeDto {
    private Long postId;
    private Long commentId;
    private String username;
}
