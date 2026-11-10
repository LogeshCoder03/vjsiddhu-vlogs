package com.teachhub.backend.dtos.EventDto;
import com.teachhub.backend.dtos.AddressDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestEventDto {
    @NotBlank
    @Pattern(regexp = "\\d{10}",message = "Mobile number must be 10 digits")
    private Long mobileNumber;
    @NotBlank
    private LocalDateTime heldOn;
    private AddressDto address;
}
