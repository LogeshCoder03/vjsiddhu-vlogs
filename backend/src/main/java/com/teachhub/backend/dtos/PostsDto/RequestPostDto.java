package com.teachhub.backend.dtos.PostsDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
