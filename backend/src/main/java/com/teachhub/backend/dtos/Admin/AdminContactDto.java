package com.teachhub.backend.dtos.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminContactDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
}
