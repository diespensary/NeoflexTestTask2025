package com.example.neoflextesttask2025.controller;

import com.example.neoflextesttask2025.dto.controller.VacationPayRequestDto;
import com.example.neoflextesttask2025.dto.controller.VacationPayResponseDto;
import com.example.neoflextesttask2025.service.VacationPayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
@Validated
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @GetMapping
    public VacationPayResponseDto calculateVacationPay(@Valid @RequestBody VacationPayRequestDto req) {
        Double avgSalary = req.getAvgSalary();
        Integer days = req.getDays();
        LocalDate startDate = req.getStartDate();
        LocalDate endDate = req.getEndDate();

        Double vacationPay;

        if (startDate != null && endDate != null) {
            vacationPay = vacationPayService.calculateByDateRange(avgSalary, startDate, endDate);
        } else if (days != null) {
            vacationPay = vacationPayService.calculateByDays(avgSalary, days);
        } else {
            throw new IllegalArgumentException("Необходимо указать либо days, либо startDate и endDate");
        }

        VacationPayResponseDto vacationPayResponseDto = new VacationPayResponseDto(vacationPay);

        return vacationPayResponseDto;
    }
}
