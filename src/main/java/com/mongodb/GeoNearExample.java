package com.mongodb;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.*;
import org.bson.Document;

public class GeoNearExample {

    public GeoNearExample() {
        this.runExample();
    }

    private void runExample() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("restaurants");
        MongoCollection<Document> collection = database.getCollection("restaurants");


        AggregateIterable<Document> iterable = collection.aggregate(
                Arrays.asList(
                        eq("$geoNear", and(eq("near",
                                and(eq("type", "Point"),
                                        eq("coordinates", Arrays.asList(-73.99279d, 40.719296d)))),
                                        eq("distanceField", "dist.calculated"),
                                        eq("query", and(eq("borough", "Bronx"),
                                                eq("cuisine", "Bakery"))),
                                        eq("spherical", true)))));

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    public static void main(String[] args) {
        new GeoNearExample();
    }
}
