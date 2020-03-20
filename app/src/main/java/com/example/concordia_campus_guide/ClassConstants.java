package com.example.concordia_campus_guide;

import android.Manifest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

public class  ClassConstants {
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // Transport types constants
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TRANSIT, WALKING, BICYCLING, DRIVING})
    public @interface TransportType {}

    public static final String TRANSIT = "transit";
    public static final String WALKING = "walking";
    public static final String BICYCLING = "bicycling";
    public static final String DRIVING = "driving";

    public static final String SGW_SHUTTLE_STOP = "1455 De Maisonneuve Blvd. W., SGW Campus";
    public static final String LOYOLA_SHUTTLE_STOP = "7137 Sherbrooke St. W., Loyola Campus";
}
