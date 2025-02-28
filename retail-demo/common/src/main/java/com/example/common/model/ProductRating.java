package com.example.common.model;

import java.util.Objects;

public class ProductRating {

    long productId;
    Double rating;

    public ProductRating() {
    }

    public ProductRating(long productId, Double rating) {
        this.productId = productId;
        this.rating = rating;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public ProductRating productId(long productId) {
        setProductId(productId);
        return this;
    }

    public ProductRating rating(Double rating) {
        setRating(rating);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductRating)) {
            return false;
        }
        ProductRating productRating = (ProductRating) o;
        return productId == productRating.productId && Objects.equals(rating, productRating.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, rating);
    }

    @Override
    public String toString() {
        return "{" +
                " productId='" + getProductId() + "'" +
                ", rating='" + getRating() + "'" +
                "}";
    }
}
