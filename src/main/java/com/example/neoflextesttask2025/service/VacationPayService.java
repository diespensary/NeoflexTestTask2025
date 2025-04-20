package com.example.neoflextesttask2025.service;

import com.example.neoflextesttask2025.util.BusinessDaysCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VacationPayService {

    private final Integer daysInAYear = 365;
    private final Integer monthsInAYear = 12;

    private final BusinessDaysCalculator businessDaysCalculator;

    public Double calculateByDateRange(Double avgSalary, LocalDate startDate, LocalDate endDate) {
        int businessDays = businessDaysCalculator.countBusinessDays(startDate, endDate);
        return calculateByDays(avgSalary, businessDays);
    }

    public Double calculateByDays(Double avgSalary, Integer days) {
        Double vacationPay = (avgSalary * monthsInAYear / daysInAYear) * days;
        return vacationPay;
    }
}
