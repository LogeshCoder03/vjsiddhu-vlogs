package com.teachhub.backend.dtos;

import com.teachhub.backend.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FeastResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer totalMembers;
    private EventStatus status;
    private AddressDto venue;
    private String logoPath;
    private LocalDateTime timing;
}
