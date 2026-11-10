package com.teachhub.backend.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String state;
    @NotBlank
    @Pattern(regexp = "\\d{6}",message = "Zip/Pin Code must be 10 digits")
    private Integer pinCode;
}
