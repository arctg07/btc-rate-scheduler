package com.arctg.grafanatest.scheduler;

import com.arctg.grafanatest.service.BinanceService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestScheduler {

    private static final String bitcoinPriceSymbol = "BTCUSDT";

    private final BinanceService binanceService;

    @Scheduled(fixedDelay = 30_000)
    @Timed("SCHEDULER")
    public void updatePrice() {
        binanceService.getPrice(bitcoinPriceSymbol);
    }
}
