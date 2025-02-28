// Copyright (c) 2023, Oracle and/or its affiliates.
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/

package com.example.ratings.service;

import com.example.common.model.ProductRating;
import com.example.ratings.clients.StoreManagerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreManagerService {

    private final StoreManagerClient storeManagerClient;

    public boolean rateProduct(ProductRating productRating) {
        return storeManagerClient.rateProduct(productRating).getStatusCode().is2xxSuccessful();
    }

}