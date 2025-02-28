package com.example.ratings.serde;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.example.ratings.model.Rating;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class RatingSerializer extends JsonSerializer<Rating> {

    @Override
    public void serialize(Rating rating, JsonGenerator jsonGenerator, SerializerProvider serializers)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(
                "productId",
                rating.getId().getProductId());
        jsonGenerator.writeNumberField(
                "userId",
                rating.getId().getUserId());
        jsonGenerator.writeNumberField(
                "userId",
                rating.getRating());
        jsonGenerator.writeEndObject();
    }
}