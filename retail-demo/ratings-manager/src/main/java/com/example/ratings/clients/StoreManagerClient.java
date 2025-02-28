package com.example.ratings.clients;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.common.model.ProductRating;

@FeignClient("store-manager")
@Configuration
public interface StoreManagerClient {

    @PostMapping("/api/v1/products/rate")
    ResponseEntity<HttpStatus> rateProduct(@RequestBody ProductRating productRating);

}