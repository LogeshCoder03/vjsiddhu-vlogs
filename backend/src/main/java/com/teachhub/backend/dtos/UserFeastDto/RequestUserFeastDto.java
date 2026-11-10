package com.teachhub.backend.dtos.UserFeastDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RequestUserFeastDto {
    @NotBlank
    @Pattern(regexp = "\\d{10}",message = "Mobile number must be 10 digits")
    private Long mobileNumber;
}
