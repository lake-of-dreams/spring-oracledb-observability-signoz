package com.example.ratings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@Import({ com.example.common.filter.LoggingFilterConfig.class })
public class RatingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingsApplication.class, args);
    }
}
