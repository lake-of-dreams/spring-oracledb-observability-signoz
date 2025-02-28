
package com.example.store.repository;

import com.example.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductId(long productId);
}