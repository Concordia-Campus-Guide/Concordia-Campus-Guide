package com.example.concordia_campus_guide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.fragments.LocationFragment;
import com.example.concordia_campus_guide.fragments.PathInfoCardFragment;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.helper.routes_helpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.helper.routes_helpers.UrlBuilder;
import com.example.concordia_campus_guide.helper.ViewModelFactory;
import com.example.concordia_campus_guide.interfaces.DirectionsApiCallbackListener;
import com.example.concordia_campus_guide.models.BusStop;
import com.example.concordia_campus_guide.models.Floor;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.routes.Route;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.view_models.PathsViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class PathsActivity extends AppCompatActivity implements DirectionsApiCallbackListener {

    private PathsViewModel mViewModel;
    private LocationFragment locationFragment;
    private FragmentManager fragmentManager;
    private DirectionsRoute directionsRoute;
    private ArrayList<DirectionWrapper> directionWrappers;
    private Place initialLocation;
    private Place destinationLocation;

    // Encoded polyline for shuttle path
    public static final String SHUTTLE_POLYLINE_SGW_LOY = "icutGlxa`MpCzBj@VNDv@T~BkHzC_JrAgEz@x@tJlJ`DvC~@|@`AtAf@_A\\u@l@_@ZIZAb@F\\N`@^tChEjHlL`A`BvDfHV`@FDTDdA~Bz@|An@jA|FnKfPfZ~ClFn@jA`@r@d@n@lBxBx@h@t@t@Zr@VXp@f@j@TbAXnAd@fCdAdAb@\\Xx@rAZb@Z\\v@r@x@t@x@nAn@zAZr@vBzCJLJBbD|ErApBJ\\@N?Jv@Zj@n@hDnFx@rAnApERZp@dBRb@vBjDfBrCVn@Rn@Nx@jBpJXpAZx@n@~AhAjCDh@Z|@Pt@Fb@?h@CtAEfA@h@Fr@f@jA`AvB~BfDHJPFr@zArAfDRf@Pr@^jBXrBnBfPj@rDb@fBNf@^bAPb@@Xb@hAQ\\sBjGuBhGeDnJI\\HPtDzInDhIzClHpBtEZj@lA`Dv@`BNXnAdBJTdDdE";
    public static final String SHUTTLE_POLYLINE_LOY_SGW = "armtGrmm`MtCjDbBpBhAnAp@l@`C`BnDjCvAqFj@gBvAeG|A}DLM{@kEy@uEoAuFa@iAsAaCq@kAY[a@iAIoAhBrA~@l@dCzAbBfAxDtBTDx@B~@Cf@Kb@Sx@k@R[HY?]EMi@_A_@u@yBgE[m@uAqBsAuBuEoHu@eA{AcCK[EKC]?OLOd@u@Va@DO@MAQEMgDsFsBkDQWkAaBkBeCqDmFWSOEeAuByA{Cc@}@cCyFuAyD_BwEiHsWu@eCs@cCSu@CCWo@_BmF{D_MiDeKwC}IeBoEiBmE}@cB{BkDy@aAs@y@_@c@c@[}BcB}@aAkCcBsCoAa@Q[_@m@q@_@]_@Sa@S_C}@_D}@{IeCiCw@eBq@oAs@qA{@{AoAwAyA[a@m@u@_@q@yAmCyAqCwCmEoAyBaLaTsHoN}BsDAa@_@y@wCqFo@mAeAsBW_@SWk@_@a@QaAc@]Uc@k@yDuFsBkCi@m@QO]Se@Is@@u@TSJc@Z[\\SVsBgBiF_FaIsHoCiCKXcAbCcCxGyBnFgB~EpAfA";

    private boolean shuttleSelected;
    int counter = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);

        initComponents();
        setPath();
        setupInfoCard();
    }

    private void setPath() {
        if (!shuttleSelected) {
            setPaths();
        } else {
            setShuttlePaths();
        }
    }

    private void initComponents() {

        directionWrappers = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(PathsViewModel.class);
        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);

        setupFromToHeaderInfoTv();
        getBundleInformation();
        setBackButtonOnClickListener();
        initLocationFragment();
    }
    
    private void initLocationFragment(){
        initialLocation = mViewModel.getInitialLocation();
        destinationLocation = mViewModel.getDestination();
        locationFragment.setDifferentInitialView(new LatLng(initialLocation.getLatitude(),
                initialLocation.getLongitude()));
    }

    private void getBundleInformation() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            directionsRoute = (DirectionsRoute) extras.getSerializable("directionsResult");
            shuttleSelected = extras.getBoolean("shuttle");
        }
    }

    private void setupFromToHeaderInfoTv() {
        final TextView initialLocationTv = findViewById(R.id.path_fromText);
        final TextView destinationTv = findViewById(R.id.path_toText);

        initialLocationTv.setText(mViewModel.getInitialLocationDisplayName());
        destinationTv.setText(mViewModel.getDestinationLocationDisplayName());
    }

    private void returnToSelectRoute() {
        final Intent returnToSelectRoute = new Intent(PathsActivity.this, RoutesActivity.class);
        startActivity(returnToSelectRoute);
    }

    private void setBackButtonOnClickListener() {
        final ImageButton backButton = findViewById(R.id.pathsPageBackButton);
        backButton.setOnClickListener(view -> returnToSelectRoute());
    }

    public void setupInfoCard() {
        final View pathInfoCard = findViewById(R.id.path_info_card);
        final BottomSheetBehavior swipeablePathCard = BottomSheetBehavior.from(pathInfoCard);
        final PathInfoCardFragment pathInfoCardFragment = new PathInfoCardFragment();

        // creating bundle to be able to pass the directionWrapper and the walkingPoints
        // to the pathsActivity
        final Bundle infoCardBundle = new Bundle();
        infoCardBundle.putSerializable("directionsResult", mViewModel.getInfoCardList());

        pathInfoCardFragment.setArguments(infoCardBundle);

        // creating fragmentTransaction to show the step-by-step card from the bottom of
        // the screen
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.path_info_card_frame, pathInfoCardFragment);
        fragmentTransaction.commit();
        swipeablePathCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public List<DirectionWrapper> parseDirectionResults() {
        final ArrayList<DirectionWrapper> directionWrapperArrayList = new ArrayList<>();
        final DirectionsStep[] steps = directionsRoute != null && directionsRoute.legs[0] != null
                ? directionsRoute.legs[0].steps
                : null;
        for (final DirectionsStep step : steps) {
            final DirectionWrapper directionWrapper = new DirectionWrapper();
            directionWrapper.populateAttributesFromStep(step);
            directionWrapperArrayList.add(directionWrapper);
        }
        return directionWrapperArrayList;
    }

    public boolean isInitialIndoor(final Place initialLocation) {
        return mViewModel.isPlaceIndoor(initialLocation);
    }

    public boolean isDestinationIndoor(final Place destinationLocation) {
        return mViewModel.isPlaceIndoor(destinationLocation);
    }

    public void setOutdoorPaths() {
        directionWrappers = (mViewModel.areInSameBuilding(initialLocation, destinationLocation)
                || mViewModel.arePlacesSeparatedByATunnel(initialLocation, destinationLocation)) ? new ArrayList<>()
                : (ArrayList<DirectionWrapper>) parseDirectionResults();

        locationFragment.drawOutdoorPaths(directionWrappers);
        mViewModel.adaptOutdoorDirectionsToInfoCardList(directionWrappers);
    }

    public boolean isPathOutdoor() {
        return !isInitialIndoor(initialLocation) && !isDestinationIndoor(destinationLocation);
    }

    public void setPaths() {

        if (isPathOutdoor()) {
            setOutdoorPaths();
            return;
        }
        // Outdoor -> Indoor
        if (!isInitialIndoor(initialLocation)) {
            setOutdoorToIndoorpath();
        } else if (!isDestinationIndoor(destinationLocation)) {
            setupIndoorPaths(initialLocation, mViewModel.getEntrance(initialLocation));
            setOutdoorPaths();
        } else if (isSharedBuilding()) {
            setupIndoorPaths(initialLocation, destinationLocation);
        } else {
            // [from -> from_entrance ] + outdoor directions + [to_entrance -> to]
            setupIndoorPaths(initialLocation, mViewModel.getEntrance(initialLocation));
            setOutdoorToIndoorpath();
        }
    }

    private boolean isSharedBuilding() {
        return mViewModel.arePlacesSeparatedByATunnel(initialLocation, destinationLocation)
                || mViewModel.areInSameBuilding(initialLocation, destinationLocation);
    }

    private void setupIndoorPaths(final Place initialLocation, final Place entrance) {
        locationFragment.setIndoorPaths(initialLocation, entrance);
        mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
    }

    private void setOutdoorToIndoorpath() {
        setOutdoorPaths();
        setupIndoorPaths(mViewModel.getEntrance(destinationLocation), destinationLocation);
    }

    public void setShuttlePaths() {
        // From starting point to bus stop
        // Indoor -> Entrance
        if (isInitialIndoor(initialLocation)) {
            setupIndoorPaths(initialLocation, mViewModel.getEntrance(initialLocation));
            getOutdoorDirections(mViewModel.getEntrance(initialLocation), new BusStop(initialLocation.getCampus()));
        } else {
            // Outdoor directions to bus stop
            getOutdoorDirections(initialLocation, new BusStop(initialLocation.getCampus()));
        }
        drawShuttlePath();
        // From the bus stop to the destination
        getOutdoorDirections(new BusStop(destinationLocation.getCampus()), mViewModel.getEntrance(destinationLocation));
    }

    public void getOutdoorDirections(final Place from, final Place to) {
        final LatLng fromLatLong = new LatLng(from.getLatitude(),
                from.getLongitude());
        final LatLng toLatLong = new LatLng(to.getLatitude(),
                to.getLongitude());

        final String url = UrlBuilder.build(fromLatLong, toLatLong, ClassConstants.WALKING,
                getBaseContext().getResources().getConfiguration().getLocales().get(0));
        new DirectionsApiDataRetrieval(PathsActivity.this).execute(url, ClassConstants.WALKING);
    }

    @Override
    public void directionsApiCallBack(final DirectionsResult result, final List<Route> routeOptions) {
        if (result.routes.length > 0) {
            directionsRoute = result.routes[0];
            directionWrappers = (ArrayList<DirectionWrapper>) parseDirectionResults();
            locationFragment.drawOutdoorPaths(directionWrappers);
            mViewModel.adaptOutdoorDirectionsToInfoCardList(directionWrappers);
        }
        if (counter < 2) {
            mViewModel.adaptShuttleToInfoCardList();
        } else if (isDestinationIndoor(destinationLocation)) {
            setupIndoorPaths(mViewModel.getEntrance(destinationLocation), destinationLocation);
        }
        counter += 1;
    }

    public void drawShuttlePath() {
        final String polyline = initialLocation.getCampus().equals("SGW") ? SHUTTLE_POLYLINE_SGW_LOY
                : SHUTTLE_POLYLINE_LOY_SGW;
        locationFragment.setShuttlePaths(polyline);
    }
}
