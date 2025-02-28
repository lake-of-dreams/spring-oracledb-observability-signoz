
package com.example.ratings.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

import com.example.ratings.serde.RatingDeserializer;
import com.example.ratings.serde.RatingSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RATINGS")
@JsonSerialize(using = RatingSerializer.class)
@JsonDeserialize(using = RatingDeserializer.class)
public class Rating {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "USER_ID")),
            @AttributeOverride(name = "productId", column = @Column(name = "PRODUCT_ID")),
    })
    private RatingId id;

    @Column(name = "RATING")
    private Double rating;

    public Rating(RatingId ratingId, Double rating) {
        this.id = ratingId;
        this.rating = rating;
    }

    public RatingId getId() {
        return this.id;
    }

    public void setId(RatingId ratingId) {
        this.id = ratingId;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Rating ratingId(RatingId ratingId) {
        setId(ratingId);
        return this;
    }

    public Rating rating(Double rating) {
        setRating(rating);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Rating)) {
            return false;
        }
        Rating aRating = (Rating) o;
        return Objects.equals(id, aRating.id) && Objects.equals(rating, aRating.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating);
    }

    @Override
    public String toString() {
        return "{" +
                " ratingId='" + getId() + "'" +
                ", rating='" + getRating() + "'" +
                "}";
    }

}