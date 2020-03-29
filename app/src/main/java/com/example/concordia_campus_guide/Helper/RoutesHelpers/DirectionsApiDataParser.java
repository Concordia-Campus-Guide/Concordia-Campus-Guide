package com.example.concordia_campus_guide.Helper.RoutesHelpers;

import android.os.AsyncTask;
import android.util.Log;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.Models.Routes.Bus;
import com.example.concordia_campus_guide.Models.Routes.Car;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Routes.Subway;
import com.example.concordia_campus_guide.Models.Routes.Train;
import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an AsyncTask because we want to avoid freezing the main UI thread when parsing the Google Maps Directions API's response
 */
public class DirectionsApiDataParser extends AsyncTask<DirectionsApiDataRetrieval, Integer, DirectionsResult> {

    private DirectionsApiDataRetrieval dataRetrieval = null;

    /**
     * Parsing the JSON string data to map it to a DirectionsResult model
     *
     * @param obj: DirectionsApiDataRetrieval object
     */
    @Override
    protected DirectionsResult doInBackground(DirectionsApiDataRetrieval... obj) {
        this.dataRetrieval = obj[0];
        DirectionsResult directionsResult = getDirectionsResultObj();
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
        dataRetrieval.getCaller().directionsApiCallBack(result, extractRelevantInfoFromDirectionsResultObj(result), dataRetrieval.getData());
    }

    private DirectionsResult getDirectionsResultObj() {
        DirectionsResult directionsResult = null;
        try {
            Log.d(DirectionsApiDataParser.class.getName(), "Mapping data to models");
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            directionsResult = gson.fromJson(dataRetrieval.getData(), DirectionsResult.class);
        } catch (Exception e) {
            Log.e(DirectionsApiDataParser.class.getName(), "Exception using Gson to map JSON to Models: " + e.toString());
        }

        return directionsResult;
    }

    private List<Route> extractRelevantInfoFromDirectionsResultObj(DirectionsResult result) {
        List<Route> routeOptions = new ArrayList<>();
        for(DirectionsRoute directionsRoute: result.routes) {
            switch(dataRetrieval.getTransportType()) {
                case ClassConstants.DRIVING:
                    extractDurationAndSummary(routeOptions, directionsRoute, ClassConstants.DRIVING);
                    break;
                case ClassConstants.WALKING:
                    extractDurationAndSummary(routeOptions, directionsRoute, ClassConstants.WALKING);
                    break;
                case ClassConstants.TRANSIT:
                    extractTransitInfo(routeOptions, directionsRoute);
                    break;
            }
        }
        return routeOptions;
    }

    // Helper methods
    private void extractDurationAndSummary(List<Route> routeOptions, DirectionsRoute directionsRoute, @ClassConstants.TransportType String transportType) {
        Route route = new Route(directionsRoute.legs[0].duration.text, directionsRoute.summary, transportType);
        routeOptions.add(route);
    }

    private void extractTransitInfo(List<Route> routeOptions, DirectionsRoute directionsRoute) {
        Route route;

        if(directionsRoute.legs[0].departureTime != null && directionsRoute.legs[0].arrivalTime != null)
            route = new Route(directionsRoute.legs[0].departureTime.text, directionsRoute.legs[0].arrivalTime.text, directionsRoute.legs[0].duration.text, ClassConstants.TRANSIT);
        else
            route = new Route(directionsRoute.legs[0].duration.text, ClassConstants.TRANSIT);

        routeOptions.add(route);

        extractSteps(directionsRoute.legs[0].steps, route);
    }

    private void extractSteps(DirectionsStep[] steps, Route route) {
        for(DirectionsStep step: steps) {
            route.getSteps().add(getTransportType(step));
        }
    }

    private TransportType getTransportType(DirectionsStep step) {
        TravelMode mode = step.travelMode;

        switch (mode) {
            case DRIVING:
                return new Car(step);
            case WALKING:
                return new Walk(step);
            case BICYCLING:
                return null;
            case TRANSIT:
                return getTransitType(step);
        }
        return null;
    }

    private TransportType getTransitType(DirectionsStep step) {
        if(step.transitDetails.line.vehicle.name.equalsIgnoreCase("bus")) {
            return new Bus(step);
        }
        else if (step.transitDetails.line.vehicle.name.equalsIgnoreCase("subway")) {
            return new Subway(step);
        }
        else if (step.transitDetails.line.vehicle.name.equalsIgnoreCase("train")) {
            return new Train(step);
        }

       return null;
    }

    public DirectionsApiDataRetrieval getDataRetrieval() {
        return dataRetrieval;
    }

    public void setDataRetrieval(DirectionsApiDataRetrieval dataRetrieval) {
        this.dataRetrieval = dataRetrieval;
    }
}