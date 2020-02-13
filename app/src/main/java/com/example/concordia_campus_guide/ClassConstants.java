package com.example.concordia_campus_guide;

import android.Manifest;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.PatternItem;

import java.util.Arrays;
import java.util.List;

public class  ClassConstants {
    public static final int COLOR_WHITE_ARGB = 0x3C967bb6;
    public static final int COLOR_GREEN_ARGB = 0xff388E3C;
    public static final int COLOR_PURPLE_ARGB = 0xff81C784;
    public static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    public static final int COLOR_BLUE_ARGB = 0xffF9A825;
    public static final int COLOR_BLACK_ARGB = 0xff000000;

    public static final int POLYGON_STROKE_WIDTH_PX = 8;
    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;

    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // Create a stroke pattern of a gap followed by a dash.
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    public static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
}
