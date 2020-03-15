package com.example.concordia_campus_guide.RoutesHelpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectionsApiDataRetrieval extends AsyncTask<String, Void, String> {
    Context mContext;
    String directionMode = "";

    public DirectionsApiDataRetrieval(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            // Fetching the data from web service
            data = getDataFromGMapsDirectionsApi(strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        // Invokes the thread for parsing the JSON data
        new DirectionsApiDataParser().execute(data);
    }

    /**
     * Get all possible routes from origin to destination using a specific transport type in a JSON format
     *
     * @param strUrl: the URL that will be used to call the Google Maps Directions API
     *
     */
    private String getDataFromGMapsDirectionsApi(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data); // Debug
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
