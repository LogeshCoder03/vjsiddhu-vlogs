package com.teachhub.backend.dtos.UserFeastDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserFeastDto {
    private String token;
    private Boolean isVerified;
    private LocalDateTime joinedAt;
}
