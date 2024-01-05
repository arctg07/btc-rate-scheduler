package com.arctg.grafanatest.client;

import com.arctg.grafanatest.model.Rate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "binance-client",
        url = "${binance.url}"
)
public interface BinanceApiClient {

    @GetMapping
    Rate getRate(@RequestParam(required = false) String symbol);
}
