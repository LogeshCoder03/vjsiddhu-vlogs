package com.teachhub.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeastDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Integer totalMembers;

    private AddressDto venue;

    private LocalDateTime timing;
}
