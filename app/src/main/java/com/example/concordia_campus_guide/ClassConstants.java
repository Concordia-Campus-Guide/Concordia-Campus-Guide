package com.example.concordia_campus_guide;

import android.Manifest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.StringDef;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.PatternItem;

public class  ClassConstants {
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String DISABILITY_BUTTON = "disability_button";
    public static final String SHARED_PREFERENCES = "UserPreferences";

    // Transport types constants
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TRANSIT, WALKING, BICYCLING, DRIVING})
    public @interface TransportType {}

    public static final String TRANSIT = "transit";
    public static final String WALKING = "walking";
    public static final String BICYCLING = "bicycling";
    public static final String DRIVING = "driving";
    public static final String SHUTTLE = "shuttle";

    public static final String SGW_SHUTTLE_STOP = "1455 De Maisonneuve Blvd. W., SGW Campus";
    public static final String LOYOLA_SHUTTLE_STOP = "7137 Sherbrooke St. W., Loyola Campus";

    //Polyline styling
    public static final List<PatternItem> WALK_PATTERN = Arrays.asList(new Gap(20), new Dash(20));

}
