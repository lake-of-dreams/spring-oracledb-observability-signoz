package com.example.store.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.example.common.model.ProductRating;
import com.example.store.model.*;
import com.example.store.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get all Products.
     *
     * @return List all products
     */
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Create an product.
     *
     * @param product Product object.
     * @return Returns HTTP Status code or the URI of the created object.
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        boolean exists = productRepository.existsById(product.getProductId());

        if (!exists) {
            try {
                Product newProduct = productRepository.saveAndFlush(product);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(newProduct.getProductId())
                        .toUri();
                return ResponseEntity.created(location).build();
            } catch (Exception e) {
                return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
    }

    /**
     * Find an product by Product Id.
     *
     * @param productId Product Id
     * @return An product
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") long productId) {
        Optional<Product> productData = productRepository.findById(productId);
        try {
            return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an Product with specific Id.
     *
     * @param productId Product ID
     * @return HTTP Status Code
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("productId") long productId) {
        try {
            productRepository.deleteById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rate a product.
     *
     * @param productRating ProductRating object.
     * @return Returns HTTP Status code.
     */
    @PostMapping("/rate")
    public ResponseEntity<?> rateProduct(@RequestBody ProductRating productRating) {
        Product product = productRepository.getReferenceById(productRating.getProductId());
        if ((productRating.getRating() != null) && (!productRating.getRating().isNaN())) {
            try {
                Double oldRatingCount = 0.0;
                if ((product.getRatingCount() == null) || (product.getRatingCount().isNaN())) {
                    product.setRatingCount(1.0);
                } else {
                    oldRatingCount = product.getRatingCount();
                    product.setRatingCount(oldRatingCount + 1.0);
                }

                if ((product.getAverageRating() == null) || (product.getAverageRating().isNaN())) {
                    product.setAverageRating(productRating.getRating());
                } else {
                    product.setAverageRating((product.getAverageRating() * oldRatingCount) / product.getRatingCount());
                }
                productRepository.saveAndFlush(product);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}