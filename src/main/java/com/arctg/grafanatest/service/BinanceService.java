package com.arctg.grafanatest.service;

import com.arctg.grafanatest.client.BinanceApiClient;
import com.arctg.grafanatest.model.RateResponse;
import com.arctg.grafanatest.repository.RateRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BinanceService {

    private MeterRegistry meterRegistry;
    private AtomicReference<BigDecimal> btsRate;
    public BinanceService(BinanceApiClient binanceApiClient, RateRepository rateRepository, MeterRegistry meterRegistry) {
        this.binanceApiClient = binanceApiClient;
        this.rateRepository = rateRepository;
        btsRate = new AtomicReference<>();
        this.meterRegistry = meterRegistry;
    }

    private final BinanceApiClient binanceApiClient;
    private final RateRepository rateRepository;

    @Timed("GET_BTC_PRICE")
    public void getPrice(String currency) {
        val rate = binanceApiClient.getRate(currency);
        rateRepository.saveRate(rate);

        val bd = new BigDecimal(rate.price()).setScale(2, RoundingMode.HALF_UP);
        btsRate.set(bd);

        Gauge.builder("btsRate", btsRate, ref -> ref.get().doubleValue())
                .description("Current rate of BTS")
                .register(meterRegistry);
    }

    @Timed
    public List<RateResponse> getAllRates() {
        return rateRepository.getRates();
    }
}
