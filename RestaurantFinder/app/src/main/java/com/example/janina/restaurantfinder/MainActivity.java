package com.example.janina.restaurantfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String jsonResult; //JSON response from API


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onFindButtonClick(View v) {

        if (isOnline()) {
            //BW handles the HTTP POST request to API
            BackgroundWorker bw = new BackgroundWorker(new BackgroundWorker.AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    //When process is finished, we get the result and pass it to next activity by calling starting function.
                    jsonResult = output;
                    System.out.println(jsonResult);
                    startRLActivity(jsonResult);


                }
            });

            bw.execute();

        } else {

            Toast.makeText(this, "No internet access", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public void startRLActivity(String json) {


        Intent intent = new Intent(this, RestaurantListActivity.class);
        intent.putExtra("jsonresult", json);
        startActivity(intent);
    }
}