package com.example.ratings.serde;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.example.ratings.model.Rating;
import com.example.ratings.model.RatingId;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;

@JsonComponent
public class RatingDeserializer extends JsonDeserializer<Rating> {

    @Override
    public Rating deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException,
            JsonProcessingException {

        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        IntNode userId = (IntNode) treeNode.get("userId");
        IntNode productId = (IntNode) treeNode.get("productId");
        DoubleNode rating = (DoubleNode) treeNode.get("rating");
        return new Rating().ratingId(new RatingId().productId(productId.asLong()).userId(userId.asLong()))
                .rating(rating.asDouble());
    }
}