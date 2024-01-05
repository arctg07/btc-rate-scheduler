package com.arctg.grafanatest.controller;

import com.arctg.grafanatest.model.RateResponse;
import com.arctg.grafanatest.service.BinanceService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/v1")
@RequiredArgsConstructor
public class InternalGrafanaController {

    private final BinanceService binanceService;

    @Timed("API_GET_RATES")
    @GetMapping("/rates")
    public List<RateResponse> getAllRates() {
        return binanceService.getAllRates();
    }
}
