package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AdminRequestUserDto {
    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    private String password;
    private LocalDateTime joinedAt;
    private List<String> roles;
}
