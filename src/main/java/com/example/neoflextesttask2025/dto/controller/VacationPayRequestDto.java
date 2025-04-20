package com.example.neoflextesttask2025.dto.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class VacationPayRequestDto {

    @NotNull(message = "avgSalary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "avgSalary must be greater than zero")
    private Double avgSalary;

    @Min(value = 1, message = "days must be at least 1")
    private Integer days;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
