package com.teachhub.backend.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {
    private String message;
}
