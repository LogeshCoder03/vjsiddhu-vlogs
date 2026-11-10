package com.teachhub.backend.dtos.Admin;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminUserFeastDto {
    private Long id;
    private Integer mobileNumber;
    private String token;
    private Boolean isVerified;
    private Long Otp;
    private LocalDateTime joinedAt;
    private String username;
    private Long feastId;
}
