package com.teachhub.backend.dtos.Admin.AdminRequestDto;

import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFeastDto {
    private String name;
    private String description;
    private Integer totalMembers;
    private AddressDto venue;
    private Boolean isLaunched;
    private LocalDateTime timing;
    private EventStatus status;
    private String username;
}
