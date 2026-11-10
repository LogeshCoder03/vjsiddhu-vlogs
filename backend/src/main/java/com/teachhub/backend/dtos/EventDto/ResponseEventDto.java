package com.teachhub.backend.dtos.EventDto;
import com.teachhub.backend.dtos.AddressDto;
import com.teachhub.backend.enums.EventStatus;
import com.teachhub.backend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEventDto {
    private Long id;
    private EventType eventType;
    private Integer mobileNumber;
    private LocalDateTime heldOn;
    private AddressDto address;
    private EventStatus eventStatus;
}
