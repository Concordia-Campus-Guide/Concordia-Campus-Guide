package com.example.concordia_campus_guide;

import android.Manifest;

import androidx.annotation.StringDef;

import com.example.concordia_campus_guide.models.Coordinates;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public class  ClassConstants {
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final String TRUE = "true";
    public static final String FALSE = "false";
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

    public static final Coordinates SGW_SHUTTLE_STOP = new Coordinates( 45.497025978950774, -73.57849985361099);
    public static final Coordinates LOYOLA_SHUTTLE_STOP = new Coordinates(45.4584776424937, -73.638241700828);

    // Encoded polyline for shuttle path
    public static final String SHUTTLE_POLYLINE_SGW_LOY = "icutGlxa`MpCzBj@VNDv@T~BkHzC_JrAgEz@x@tJlJ`DvC~@|@`AtAf@_A\\u@l@_@ZIZAb@F\\N`@^tChEjHlL`A`BvDfHV`@FDTDdA~Bz@|An@jA|FnKfPfZ~ClFn@jA`@r@d@n@lBxBx@h@t@t@Zr@VXp@f@j@TbAXnAd@fCdAdAb@\\Xx@rAZb@Z\\v@r@x@t@x@nAn@zAZr@vBzCJLJBbD|ErApBJ\\@N?Jv@Zj@n@hDnFx@rAnApERZp@dBRb@vBjDfBrCVn@Rn@Nx@jBpJXpAZx@n@~AhAjCDh@Z|@Pt@Fb@?h@CtAEfA@h@Fr@f@jA`AvB~BfDHJPFr@zArAfDRf@Pr@^jBXrBnBfPj@rDb@fBNf@^bAPb@@Xb@hAQ\\sBjGuBhGeDnJI\\HPtDzInDhIzClHpBtEZj@lA`Dv@`BNXnAdBJTdDdE";
    public static final String SHUTTLE_POLYLINE_LOY_SGW = "armtGrmm`MtCjDbBpBhAnAp@l@`C`BnDjCvAqFj@gBvAeG|A}DLM{@kEy@uEoAuFa@iAsAaCq@kAY[a@iAIoAhBrA~@l@dCzAbBfAxDtBTDx@B~@Cf@Kb@Sx@k@R[HY?]EMi@_A_@u@yBgE[m@uAqBsAuBuEoHu@eA{AcCK[EKC]?OLOd@u@Va@DO@MAQEMgDsFsBkDQWkAaBkBeCqDmFWSOEeAuByA{Cc@}@cCyFuAyD_BwEiHsWu@eCs@cCSu@CCWo@_BmF{D_MiDeKwC}IeBoEiBmE}@cB{BkDy@aAs@y@_@c@c@[}BcB}@aAkCcBsCoAa@Q[_@m@q@_@]_@Sa@S_C}@_D}@{IeCiCw@eBq@oAs@qA{@{AoAwAyA[a@m@u@_@q@yAmCyAqCwCmEoAyBaLaTsHoN}BsDAa@_@y@wCqFo@mAeAsBW_@SWk@_@a@QaAc@]Uc@k@yDuFsBkCi@m@QO]Se@Is@@u@TSJc@Z[\\SVsBgBiF_FaIsHoCiCKXcAbCcCxGyBnFgB~EpAfA";

    //Polyline styling
    public static final List<PatternItem> WALK_PATTERN = Arrays.asList(new Gap(20), new Dash(20));

    //EV centerCoordinates
    public static final LatLng INITIAL_ZOOM_LOCATION = new LatLng(45.495638 ,-73.578258);

    //SGW building label string used as campus center
    public static final String SGW_CENTER_BUILDING_LABEL = "EV";

    //Loyola building label string used as campus center
    public static final String LOYOLA_CENTER_BUILDING_LABEL = "CC";

    public static final int EARTH_RADIUS_KM = 6371;

}
