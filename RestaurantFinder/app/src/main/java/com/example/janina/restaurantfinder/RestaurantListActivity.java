package com.example.janina.restaurantfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


public class RestaurantListActivity extends AppCompatActivity {


    public LinkedHashMap<String, Timetable> timetableMap; // where we save restaurant's timetables
    public Timetable timetable;
    public ArrayList<Restaurant> restArrayList; //List of restaurant objects.
    String jsonResult;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        jsonResult = getIntent().getStringExtra("jsonresult");
        restaurantArrayFromJson(jsonResult);
    }

    public void restaurantArrayFromJson(String json){

        try {

            JSONArray restaurantJSONArray = new JSONArray(json); //Create a json array of the result.

            restArrayList = new ArrayList<Restaurant>();

            //Loop through JSON array and create json objects from its content.
            for (int i = 0; i < restaurantJSONArray.length(); i++) {

                JSONObject restaurantObject = restaurantJSONArray.getJSONObject(i);

                //Opening hours to Timetable object
                JSONObject timetable = (JSONObject) restaurantJSONArray.getJSONObject(i).get("timetable");
                JSONArray opening_hours = (JSONArray) timetable.get("opening_hours");
                timetableMap = new LinkedHashMap<String, Timetable>();

                for (int a=0; a < opening_hours.length(); a++) {

                    JSONObject week_day = opening_hours.getJSONObject(a);
                    String weekdayKey = String.valueOf(opening_hours.getJSONObject(a).getString("name"));
                    this.timetable = new Timetable(week_day);
                    timetableMap.put(weekdayKey, this.timetable);
                }

                //Create new Restaurant object and add it to restaurant array list.
                restArrayList.add(new Restaurant(restaurantObject, timetableMap));

            }

            listRestaurants();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public void listRestaurants() {

        Collections.reverse(restArrayList); // Reverse, so we can get the furthest restaurant first. Restaurant class is comparable.

        listView = (ExpandableListView)findViewById(R.id.rest_listView);

        RestaurantEAdapter adapter = new RestaurantEAdapter(RestaurantListActivity.this, restArrayList);

        listView.setAdapter(adapter);


    }





}
