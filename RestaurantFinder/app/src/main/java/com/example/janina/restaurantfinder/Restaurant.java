package com.example.janina.restaurantfinder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Janina on 3.3.2017.
 */

public class Restaurant implements Comparable<Restaurant>{

    String id;
    String name;
    String delivery_range;
    double distance;
    LinkedHashMap<String, Timetable> timeTableMap;
    boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String distanceToString() {

        String distanceString = String.valueOf(distance);
        return distanceString;

    }


    public LinkedHashMap<String, Timetable> getTimeTableMap() {
        return timeTableMap;
    }

    public void setTimeTableMap(LinkedHashMap<String, Timetable> timeTableMap) {
        this.timeTableMap = timeTableMap;
    }

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



    public Restaurant(JSONObject obj, LinkedHashMap<String, Timetable> ttMap) {

        try {
            this.id = obj.get("restaurant_id").toString();
            this.name = obj.get("name").toString();
            this.delivery_range = obj.get("delivery_range").toString();
            this.distance = Double.parseDouble(obj.get("distance").toString());
            this.timeTableMap = ttMap;
            this.isOpen = Boolean.parseBoolean(obj.getJSONObject("timetable").get("restaurant_open").toString());


        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @Override
    public int compareTo(Restaurant o) {
        return new Double(distance).compareTo(o.distance);
    }


}
