package com.arctg.grafanatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
public class GrafanaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrafanaTestApplication.class, args);
    }

}
