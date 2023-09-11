package com.example.miniproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull(message = "Review is required")
    private String reviewText;

    @NotNull(message = "Rating is required")
    @DecimalMax("10.0") @DecimalMin("0.0")
    private Double rating;
}
