package com.example.janina.restaurantfinder;

import org.json.JSONObject;

/**
 * Created by Janina on 5.3.2017.
 */

public class Timetable {

    public String name;
    public String openingTime;
    public String closingTime;

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }


    public Timetable(JSONObject obj) {
        try {
            this.name = obj.getString("name").toString();
            this.openingTime = obj.getString("open").toString();
            this.closingTime = obj.getString("close").toString();
        } catch (Exception e)
        {

            System.out.println("Timetable ex " + e);

        }
    }


}
