package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AdminUserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime joinedAt;
    private List<String> roles;
}
