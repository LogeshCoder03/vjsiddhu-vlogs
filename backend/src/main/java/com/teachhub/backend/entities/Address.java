package com.teachhub.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String city;
    private String district;
    private String state;
    private Integer pinCode;
}
