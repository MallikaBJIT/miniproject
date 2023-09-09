package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Body is required")
    private String body;
    @NotNull(message = "Available field is required")
    private boolean available;
}
