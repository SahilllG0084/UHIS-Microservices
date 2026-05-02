package com.cjc.app.dtos;

import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanRequestDto {
    	
    @NotBlank(message = "Plan name is required")
    @Size(min = 2, max = 50, message = "Plan name must be 2–50 characters")
    private String planName;

    @NotBlank(message = "Category is required")
    @Size(min = 2, max = 30, message = "Category must be 2–30 characters")
    private String category;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
}
