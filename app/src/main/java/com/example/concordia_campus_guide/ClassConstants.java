package com.example.concordia_campus_guide;

import androidx.annotation.StringDef;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.PatternItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public class  ClassConstants {
    public static boolean MY_LOCATION_PERMISSION_GRANTED = false;
    public static final String DISABILITY_BUTTON = "disability_button";
    public static final String CALENDAR_INTEGRATION_BUTTON = "calendar_integration_button";
    public static final String SHARED_PREFERENCES = "UserPreferences";
    public static final String IGNORE ="ignore";

    // Toggle buttons constants
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ACCESSIBILITY_TOGGLE, TRANSLATION_TOGGLE, STAFF_TOGGLE})
    public @interface ToggleType {}

    public static final String ACCESSIBILITY_TOGGLE = "accessibility_toggle";
    public static final String TRANSLATION_TOGGLE = "translation_toggle";
    public static final String STAFF_TOGGLE = "staff_toggle";

    // Transport types constants
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TRANSIT, WALKING, BICYCLING, DRIVING, SHUTTLE})
    public @interface TransportType {}

    public static final String TRANSIT = "transit";
    public static final String WALKING = "walking";
    public static final String BICYCLING = "bicycling";
    public static final String DRIVING = "driving";
    public static final String SHUTTLE = "shuttle";

    public static final String FLOORS_AVAILABLE = "floorsAvailable";

    //Polyline styling
    public static final List<PatternItem> WALK_PATTERN = Arrays.asList(new Gap(20), new Dash(20));

    //SGW building label string used as campus center
    public static final String SGW_CENTER_BUILDING_LABEL = "EV";
    //Loyola building label string used as campus center
    public static final String LOYOLA_CENTER_BUILDING_LABEL = "CC";

    public static final String LOCATION_FRAGMENT_TAG = "LocationFragment";
    public static final String POI_TAG = "POI";
    public static final String ROOM_TAG = "ROOM";
}
