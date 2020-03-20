package com.example.concordia_campus_guide.Helper.RoutesHelpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class is an AsyncTask because we want to avoid freezing the main UI thread when parsing the Google Maps Directions API's response
 */
public class DirectionsApiDataParser extends AsyncTask<DirectionsApiDataRetrieval, Integer, DirectionsResult> {

    /**
     * Parsing the JSON string data to map it to a DirectionsResult model
     *
     * @param obj: DirectionsApiDataRetrieval object
     */
    DirectionsApiDataRetrieval dataRetrieval = null;

    @Override
    protected DirectionsResult doInBackground(DirectionsApiDataRetrieval... obj) {
        this.dataRetrieval = obj[0];
        DirectionsResult directionsResult = null;

        try {
            Log.d(DirectionsApiDataParser.class.getName(), "Mapping data to models");

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            directionsResult = gson.fromJson(dataRetrieval.data, DirectionsResult.class);

        } catch (Exception e) {
            Log.e(DirectionsApiDataParser.class.getName(), "Exception using Gson to map JSON to Models: " + e.toString());
        }

        return directionsResult;
    }


    /**
     * On the main UI thread, after the parsing process is done, set the DirectionsResult variable in the RoutesActivity to the result obtained
     *
     * @param result: DirectionsResult object
     *
     */
    @Override
    protected void onPostExecute(DirectionsResult result) {
        dataRetrieval.caller.directionsApiCallBack(result);
    }
}