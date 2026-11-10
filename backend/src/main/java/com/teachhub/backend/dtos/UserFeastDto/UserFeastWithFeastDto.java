package com.teachhub.backend.dtos.UserFeastDto;

import com.teachhub.backend.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserFeastWithFeastDto {
    private Long id;
    private String eventName;
    private String eventLogo;
    private LocalDateTime eventTiming;
    private EventStatus eventStatus;
    private Boolean isVerified;
}
