package com.example.janina.restaurantfinder;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Janina on 3.3.2017.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {


    public String result;



    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;


    public BackgroundWorker(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected String doInBackground(String... params) {

        String url = "https://testapi.fiidmi.fi/3.0/restaurant/list";


        try {

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

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();

            //printing result from response
            System.out.println(response.toString());
            result = response.toString();


        } catch (Exception e) {

            System.out.println("mikä meni väärin" + e);

        }
        return result;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onPostExecute(String s) {



        delegate.processFinish(s);

    }


}
