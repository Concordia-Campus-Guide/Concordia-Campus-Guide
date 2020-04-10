package com.example.concordia_campus_guide.helper.routes_helpers;

import android.os.AsyncTask;
import android.util.Log;
import com.example.concordia_campus_guide.interfaces.DirectionsApiCallbackListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is an AsyncTask because we want to avoid freezing the main UI thread when retrieving the Google Maps Directions API's response
 */
public class DirectionsApiDataRetrieval extends AsyncTask<String, Void, String> {

    private DirectionsApiCallbackListener caller;
    private String data;
    private String transportType;

    public DirectionsApiDataRetrieval(DirectionsApiCallbackListener caller) { this.caller = caller; }

    /**
     * Fetching the data from web service in the background
     *
     * @param strings: strings that have been passed when the execute method was called on a DirectionsApiDataRetrieval object. In that case, string[0] is the URL to call the Google Maps Directions API
     *
     */
    @Override
    protected String doInBackground(String... strings) {
        String dataFromApi = "";
        try {
            dataFromApi = getDataFromGMapsDirectionsApi(strings[0]);
            this.transportType = strings[1];
        } catch (Exception e) {
            Log.e(DirectionsApiDataRetrieval.class.getName(), e.toString());
        }
        return dataFromApi;
    }

    /**
     * Invokes the thread for parsing the JSON data after the background task has been completed
     *
     * @param data: JSON data in string format that was retrieved from the Google Maps Directions API
     *
     */
    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        this.data = data;
        new DirectionsApiDataParser().execute(this);
    }

    /**
     * Get all possible routes from origin to destination using a specific transport type in a JSON string format
     *
     * @param strUrl: the URL that will be used to call the Google Maps Directions API
     *
     */
    private String getDataFromGMapsDirectionsApi(String strUrl) throws IOException {
        String dataFromApi = "";
        URL url = new URL(strUrl);

        // Creating an http connection to communicate with url
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Connecting to url
        urlConnection.connect();

        // Reading data from url
        InputStream iStream = urlConnection.getInputStream();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(iStream))) {
            Log.d(DirectionsApiDataRetrieval.class.getName(), "Fetching routes");
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            dataFromApi = sb.toString();
            Log.d(DirectionsApiDataRetrieval.class.getName(), "Downloaded URL: " + dataFromApi);
        } catch (Exception e) {
            Log.e(DirectionsApiDataRetrieval.class.getName(), "Exception fetching the dataFromApi from the Google MAps Directions API: " + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return dataFromApi;
    }

    public DirectionsApiCallbackListener getCaller() {
        return caller;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTransportType() {
        return transportType;
    }
}