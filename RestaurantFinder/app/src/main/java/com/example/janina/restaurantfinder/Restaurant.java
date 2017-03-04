package com.example.janina.restaurantfinder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Janina on 3.3.2017.
 */

public class Restaurant {

    String id;
    String name;
    String delivery_range;
    double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelivery_range() {
        return delivery_range;
    }

    public void setDelivery_range(String delivery_range) {
        this.delivery_range = delivery_range;
    }


    public Restaurant(JSONObject obj) {

        try {
            this.id = obj.get("restaurant_id").toString();
            this.name = obj.get("name").toString();
            this.delivery_range = obj.get("delivery_range").toString();
            this.distance = Double.parseDouble(obj.get("distance").toString());
        } catch (JSONException e) {
            System.out.println("restaurantissa vika");
        }

    }
}
