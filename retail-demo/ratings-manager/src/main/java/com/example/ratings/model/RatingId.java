package com.example.ratings.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RatingId implements Serializable {
    private long productId;
    private long userId;

    public RatingId() {
    }

    public RatingId(long productId, long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public RatingId productId(long productId) {
        setProductId(productId);
        return this;
    }

    public RatingId userId(long userId) {
        setUserId(userId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RatingId)) {
            return false;
        }
        RatingId ratingId = (RatingId) o;
        return productId == ratingId.productId && userId == ratingId.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId);
    }

    @Override
    public String toString() {
        return "{" +
                " productId='" + getProductId() + "'" +
                ", userId='" + getUserId() + "'" +
                "}";
    }

}