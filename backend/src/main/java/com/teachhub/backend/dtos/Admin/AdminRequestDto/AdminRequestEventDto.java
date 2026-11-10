package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.enums.EventType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminRequestEventDto {
    private Long id;
    @Min(10)
    private Long mobileNumber;
    @NotNull
    private LocalDateTime heldOn;
    private AddressDto address;
    @NotNull
    private String username;
    private Boolean isAccepted;
    private Long price;
    private EventType eventType;
}
