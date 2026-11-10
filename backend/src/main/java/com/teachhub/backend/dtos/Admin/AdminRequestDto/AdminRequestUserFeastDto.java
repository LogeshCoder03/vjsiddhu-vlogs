package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRequestUserFeastDto {
    @NotNull
    private Long mobileNumber;
    private String token;
    private Boolean isVerified;
    private Long otp;
    private String username;
    private Long feastId;
}
