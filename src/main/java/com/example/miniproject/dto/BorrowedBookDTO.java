package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookDTO {
    @NotNull(message = "Borrowed date is required")
    private Date borrowedDate;

    @NotNull(message = "Due date is required")
    private Date dueDate;
}
