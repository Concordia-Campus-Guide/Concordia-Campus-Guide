package com.example.concordia_campus_guide.helper.routes_helpers;

import android.os.AsyncTask;
import android.util.Log;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.models.helpers.RouteBuilder;
import com.example.concordia_campus_guide.models.helpers.RouteBuilderDirector;
import com.example.concordia_campus_guide.models.routes.Bus;
import com.example.concordia_campus_guide.models.routes.Car;
import com.example.concordia_campus_guide.models.routes.Route;
import com.example.concordia_campus_guide.models.routes.Subway;
import com.example.concordia_campus_guide.models.routes.Train;
import com.example.concordia_campus_guide.models.routes.TransportType;
import com.example.concordia_campus_guide.models.routes.Walk;
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
    private RouteBuilderDirector director = new RouteBuilderDirector();

    /**
     * Parsing the JSON string data to map it to a DirectionsResult model
     *
     * @param obj: DirectionsApiDataRetrieval object
     */
    @Override
    protected DirectionsResult doInBackground(DirectionsApiDataRetrieval... obj) {
        this.dataRetrieval = obj[0];
        return getDirectionsResultObj();
    }

    /**
     * On the main UI thread, after the parsing process is done, set the DirectionsResult variable in the RoutesActivity to the result obtained
     *
     * @param result: DirectionsResult object
     *
     */
    @Override
    protected void onPostExecute(DirectionsResult result) {
        dataRetrieval.getCaller().directionsApiCallBack(result, extractRelevantInfoFromDirectionsResultObj(result));
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
                    routeOptions.add(getDrivingRoute(directionsRoute));
                    break;
                case ClassConstants.WALKING:
                    routeOptions.add(getWalkingRoute(directionsRoute));
                    break;
                case ClassConstants.TRANSIT:
                    routeOptions.add(getTransitRoute(directionsRoute));
                    break;
                default:
                    break;
            }
        }
        return routeOptions;
    }

    // Helper methods
    private Route getDrivingRoute(DirectionsRoute directionsRoute) {
        RouteBuilder routeBuilder = new RouteBuilder();
        director.constructDrivingRoute(routeBuilder, directionsRoute.legs[0].duration.text, directionsRoute.summary);
        return routeBuilder.getRoute();
    }

    private Route getWalkingRoute(DirectionsRoute directionsRoute) {
        RouteBuilder routeBuilder = new RouteBuilder();
        director.constructWalkingRoute(routeBuilder, directionsRoute.legs[0].duration.text, directionsRoute.summary);
        return routeBuilder.getRoute();
    }

    private Route getTransitRoute(DirectionsRoute directionsRoute) {
        RouteBuilder routeBuilder = new RouteBuilder();

        if(directionsRoute.legs[0].departureTime != null && directionsRoute.legs[0].arrivalTime != null)
            director.constructTransitRoute(routeBuilder, extractSteps(directionsRoute.legs[0].steps), directionsRoute.legs[0].departureTime.text, directionsRoute.legs[0].arrivalTime.text, directionsRoute.legs[0].duration.text);
        else
            director.constructTransitRouteWithoutArrivalOrDepartureTime(routeBuilder, extractSteps(directionsRoute.legs[0].steps), directionsRoute.legs[0].duration.text);

        return routeBuilder.getRoute();
    }

    private List<TransportType> extractSteps(DirectionsStep[] stepsArr) {
        List<TransportType> steps = new ArrayList<>();
        for(DirectionsStep step: stepsArr) {
            steps.add(getTransportType(step));
        }

        return steps;
    }

    private TransportType getTransportType(DirectionsStep step) {
        TravelMode mode = step.travelMode;

        switch (mode) {
            case DRIVING:
                return new Car(step);
            case WALKING:
                return new Walk(step);
            case BICYCLING:
            case UNKNOWN:
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