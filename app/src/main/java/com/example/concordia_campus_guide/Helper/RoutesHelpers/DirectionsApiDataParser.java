package com.example.concordia_campus_guide.Helper.RoutesHelpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.GoogleMapsServicesModels.DirectionsResult;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class is an AsyncTask because we want to avoid freezing the main UI thread when parsing the Google Maps Directions API's response
 */
public class DirectionsApiDataParser extends AsyncTask<DirectionsApiDataRetrieval, Integer, DirectionsResult> {

    /**
     * Parsing the JSON stirng data to map it to a DirectionsResult model
     *
     * @param obj: strings that have been passed when the execute method was called on a DirectionsApiDataParser object. In that case, jsonData[0] is the JSON data retrieved from Google Maps Directions API
     *
     */
    DirectionsApiDataRetrieval dataRetrieval = null;

    @Override
    protected DirectionsResult doInBackground(DirectionsApiDataRetrieval... obj) {
        this.dataRetrieval = obj[0];
        DirectionsResult directionsResult = null;

        try {
            Log.d(ClassConstants.MY_LOG, "Executing routes"); // Debug

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            directionsResult = gson.fromJson(dataRetrieval.data, DirectionsResult.class);

        } catch (Exception e) {
            Log.e(ClassConstants.MY_LOG, "Exception using Gson to map JSON to Models: " + e.toString());
        }

        return directionsResult;
    }


    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(DirectionsResult result) {
        dataRetrieval.caller.directionsApiCallBack(result);
    }
}