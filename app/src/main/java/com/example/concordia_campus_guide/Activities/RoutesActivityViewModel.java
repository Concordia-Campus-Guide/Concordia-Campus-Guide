package com.example.concordia_campus_guide.Activities;

import android.location.Location;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.Models.Place;
import com.google.android.gms.maps.model.LatLng;

public class RoutesActivityViewModel extends ViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;

    private Location myCurrentLocation;
    private DirectionsResult directionsResult;

    public RoutesActivityViewModel(AppDatabase appDb) {
        this.appDB = appDb;
    }

    public void setTo(Place place){
        this.to = place;
    }

    public Place getTo(){
        return to;
    }

    public Place getFrom(){
        return from;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public DirectionsResult getDirectionsResult() {
        return directionsResult;
    }

    public void setDirectionsResult(DirectionsResult directionsResult) {
        this.directionsResult = directionsResult;
    }

    /**
     * Build the URL that will be used to call the Google Maps Directions API by passing the necessary parameters
     *
     * @param from: latitude and longitude of the origin
     * @param to: latitude and longitude of the destination
     * @param transportType: main transport type to get from origin to destination
     *
     */
    public String buildUrl(LatLng from, LatLng to, @ClassConstants.TransportType String transportType) {
        String str_origin = "origin=" + from.latitude + "," + from.longitude;
        String str_dest = "destination=" + to.latitude + "," + to.longitude;
        String mode = "mode=" + transportType;
        String alternatives = "alternatives=true";
        String parameters = str_origin + "&" + str_dest + "&" + mode + "&" + alternatives;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + BuildConfig.API_KEY;
    }



}