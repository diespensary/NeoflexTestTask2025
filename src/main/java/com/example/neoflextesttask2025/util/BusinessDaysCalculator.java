package com.example.neoflextesttask2025.util;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BusinessDaysCalculator {
    private final HolidayManager holidayManager = HolidayManager.getInstance(HolidayCalendar.RUSSIA);

    private boolean isHoliday(LocalDate date) {
        Set<LocalDate> holidays = holidayManager.getHolidays(date.getYear())
                .stream().map(Holiday::getDate).collect(Collectors.toSet());
        return holidays.contains(date);
    }

    public int countBusinessDays(LocalDate start, LocalDate end) {
        int businessDays = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DayOfWeek dow = date.getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY && !isHoliday(date)) {
                businessDays++;
            }
        }
        return businessDays;
    }
}
