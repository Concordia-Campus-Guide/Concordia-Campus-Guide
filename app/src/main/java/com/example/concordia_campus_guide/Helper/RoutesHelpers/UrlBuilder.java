package com.example.concordia_campus_guide.Helper.RoutesHelpers;

import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.ClassConstants;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class UrlBuilder {
    /**
     * Build the URL that will be used to call the Google Maps Directions API by passing the necessary parameters
     *  @param from : latitude and longitude of the origin
     * @param to : latitude and longitude of the destination
     * @param transportType : main transport type to get from origin to destination
     * @param locale: language of the response
     *
     */
    public static String build(LatLng from, LatLng to, @ClassConstants.TransportType String transportType, Locale locale) {
        String str_origin = "origin=" + from.latitude + "," + from.longitude;
        String str_dest = "destination=" + to.latitude + "," + to.longitude;
        String mode = "mode=" + transportType;
        String alternatives = "alternatives=true";
        String language = "language=" + locale.toString();
        String parameters = str_origin + "&" + str_dest + "&" + mode + "&" + alternatives + "&" + language;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + BuildConfig.API_KEY;
    }
}
