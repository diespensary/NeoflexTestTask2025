package com.example.neoflextesttask2025.controller;

import com.example.neoflextesttask2025.dto.controller.VacationPayRequestDto;
import com.example.neoflextesttask2025.service.VacationPayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VacationPayController.class)
class VacationPayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VacationPayService vacationPayService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidByDays_thenReturnsVacationPay() throws Exception {
        VacationPayRequestDto req = new VacationPayRequestDto();
        req.setAvgSalary(40000.0);
        req.setDays(5);

        double expected = 5000.0;
        when(vacationPayService.calculateByDays(req.getAvgSalary(), req.getDays()))
                .thenReturn(expected);

        mockMvc.perform(get("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(expected));

        verify(vacationPayService).calculateByDays(req.getAvgSalary(), req.getDays());
    }

    @Test
    void whenMissingAvgSalary_thenReturnsBadRequest() throws Exception {
        VacationPayRequestDto req = new VacationPayRequestDto();
        req.setDays(3);

        mockMvc.perform(get("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.avgSalary").exists());
    }

    @Test
    void whenValidByDateRange_thenReturnsVacationPay() throws Exception {
        VacationPayRequestDto req = new VacationPayRequestDto();
        req.setAvgSalary(60000.0);
        req.setStartDate(LocalDate.of(2025, 5, 1));
        req.setEndDate(LocalDate.of(2025, 5, 5));

        double expected = 3000.0;
        when(vacationPayService.calculateByDateRange(
                req.getAvgSalary(), req.getStartDate(), req.getEndDate()))
                .thenReturn(expected);

        mockMvc.perform(get("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(expected));

        verify(vacationPayService)
                .calculateByDateRange(req.getAvgSalary(), req.getStartDate(), req.getEndDate());
    }
}