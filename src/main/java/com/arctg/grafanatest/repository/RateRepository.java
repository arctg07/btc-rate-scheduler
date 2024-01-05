package com.arctg.grafanatest.repository;

import com.arctg.grafanatest.model.Rate;
import com.arctg.grafanatest.model.RateResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RateRepository {

    private static final String RATE_PREFIX = "rate:v1:";
    private static final TypeReference<Map<String, Object>> COIN_RATE_TYPE_REF = new TypeReference<>() {
    };
    private static final Duration EXPIRE_TIME = Duration.ofDays(30);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveRate(Rate rate) {
        val rateEntity = objectMapper.convertValue(rate, COIN_RATE_TYPE_REF);
        val compoundKey = getCompoundKey();
        redisTemplate.opsForHash().putAll(compoundKey, rateEntity);
        redisTemplate.expire(compoundKey, EXPIRE_TIME);

    }

    public List<RateResponse> getRates() {
        val keys = redisTemplate.keys(RATE_PREFIX + "*");

        if (keys == null) {
            return emptyList();
        }

        return keys.stream()
                .map(key -> {
                    val rate = redisTemplate.opsForHash().entries(key);
                    val timestamp = key.substring(RATE_PREFIX.length());
                    val symbol = (String) rate.get("symbol");
                    val price = (String) rate.get("price");
                    return new RateResponse(timestamp, symbol, price);
                })
                .toList();
    }

    private String getCompoundKey() {
        return RATE_PREFIX + Instant.now();
    }
}
