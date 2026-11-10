package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.enums.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminFeastDto {
    private Long id;
    private String name;
    private String description;
    private Integer totalMembers;
    private Boolean isLaunched;
    private AddressDto venue;
    private LocalDateTime timing;
    private EventStatus status;
    private String username;
    private String logoPath;
    private LocalDateTime createdAt;
    private LocalDateTime launchedAt;
}
