package com.teachhub.backend.dtos.Admin;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.enums.EventType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminEventDto {
    private Long id;
    private Integer mobileNumber;
    private LocalDateTime heldOn;
    private AddressDto address;
    private String username;
    private Boolean isAccepted;
    private Long price;
    private EventType eventType;
    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
}
