package com.example.janina.restaurantfinder;

import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Type;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Restaurant> restArrayList;
    String jsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onFindButtonClick(View v) {

        BackgroundWorker bw = new BackgroundWorker(new BackgroundWorker.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                jsonResult = output;
                System.out.println(jsonResult);
                arrayFromJson(jsonResult);



            }
        });

        bw.execute();


    }


    /*private class PostClass extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(String... params) {
            try {

                String url = "https://testapi.fiidmi.fi/3.0/restaurant/list";

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                String urlParameters = "address=\"Kauppakaari 15\"&city=\"Kerava\"";

                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

                final String USER_AGENT = "Mozilla/5.0";

                // Setting basic post request
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");
                //String postJsonData = "{\"id\":5,\"countryName\":\"USA\",\"population\":8000}";
                con.setRequestProperty("Content-Length", Integer.toString(postData.length));

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.write(postData);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post Data : " + postData);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();

                //printing result from response
                System.out.println(response.toString());


                JSONArray restaurantArray = new JSONArray(response.toString());


                restArr = new ArrayList<Restaurant>();

                for (int i = 0; i < restaurantArray.length(); i++) {

                    JSONObject restaurantObject = restaurantArray.getJSONObject(i);

                    restArr.add(new Restaurant(restaurantObject));

                }


            } catch (Exception e) {

                System.out.println("mikä meni väärin" + e);

            }
            return null;
        }


    }*/

    public void arrayFromJson(String json){

        try {

            JSONArray restaurantJSONArray = new JSONArray(json);


            restArrayList = new ArrayList<Restaurant>();

            for (int i = 0; i < restaurantJSONArray.length(); i++) {

                JSONObject restaurantObject = restaurantJSONArray.getJSONObject(i);

                restArrayList.add(new Restaurant(restaurantObject));

            }


            System.out.println(restArrayList);


        } catch (Exception e) {


        }
    }

    public void setText(String txt) {

        //final TextView outputView = (TextView) findViewById(R.id.result_text);


        EditText outputView = (EditText) findViewById(R.id.editText);
        outputView.setText(txt);

    }


}
