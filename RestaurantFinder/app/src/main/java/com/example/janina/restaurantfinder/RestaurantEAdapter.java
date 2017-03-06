package com.example.janina.restaurantfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Janina on 5.3.2017.
 */

public class RestaurantEAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Restaurant> mDataSource;

    public RestaurantEAdapter(Context mContext, ArrayList<Restaurant> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataSource.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataSource.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Restaurant restaurant = (Restaurant) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_group, null);
        }


        // Get title element
        TextView titleTextView =
                (TextView) convertView.findViewById(R.id.restaurant_list_title);

        // Get subtitle element
        TextView subtitleTextView =
                (TextView) convertView.findViewById(R.id.restaurant_list_subtitle);

        String nextDay = getNextDayOfWeek();

        Calendar calendar1 = Calendar.getInstance();
        String open = restaurant.getTimeTableMap().get(nextDay.toLowerCase()).getOpeningTime().toString();
        String close = restaurant.getTimeTableMap().get(nextDay.toLowerCase()).getClosingTime().toString();
        String isOpen = "";

        String tomorrowTime = "22.01";

        try {
            SimpleDateFormat format = new SimpleDateFormat("HH.mm");


            Date openTime = format.parse(open);
            Date closeTime = format.parse(close);
            Date checkTime = format.parse(tomorrowTime);

            Calendar calendar12 = Calendar.getInstance();
            calendar1.setTime(openTime);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(closeTime);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            Date x = calendar3.getTime();
            if (x.after(calendar12.getTime()) && x.before(calendar2.getTime())) {

            isOpen = "Open tomorrow, " + nextDay + ", at 22.01";
            } else {

                isOpen = "Closed tomorrow, " + nextDay + ", at 22.01";

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        titleTextView.setText(restaurant.name);
        subtitleTextView.setText("Id: " + restaurant.id + " Distance: " + restaurant.distanceToString() + "\n" + isOpen);



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {


            Restaurant restaurant = (Restaurant) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.restaurant_list_item, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.timetable);

            ImageView thumbnailImageView =
                    (ImageView) convertView.findViewById(R.id.restaurant_open_thumbnail);


            String openingHours = "";

            for (Map.Entry<String, Timetable> entry : restaurant.timeTableMap.entrySet()) {
                String day = entry.getKey();
                Timetable value = entry.getValue();
                String open = value.getOpeningTime();
                String close = value.getClosingTime();

                openingHours = openingHours + day + ": " + open + " - " + close + "\n";
            }

            txtListChild.setText(openingHours);
            if (restaurant.isOpen()) {
                thumbnailImageView.setImageResource(R.drawable.open);
            } else {

                thumbnailImageView.setImageResource(R.drawable.close);
            }

            return convertView;

        } catch (Exception e) {

            System.out.println(e);
            return null;
        }


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private String getNextDayOfWeek() {

        String daysArray[] = {"Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday", "Sunday"};

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        day = day-1;

        if (day == 0){

            day=6;
        }

        System.out.println(daysArray[day]);

        return daysArray[day];



    }

}
