package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Fragments.PathInfoCardFragment.PathInfoCardFragment;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.UrlBuilder;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Interfaces.DirectionsApiCallbackListener;
import com.example.concordia_campus_guide.Models.BusStop;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.R;
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
    private Place from;
    private Place to;
    private boolean fromIsIndoor = false;
    private boolean toIsIndoor = false;
    boolean shuttleSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);
        init();
        checkFromToType(from, to);
        if (!shuttleSelected) {
            setPaths();
        } else {
            setShuttlePaths();
        }
        showInfoCard();
    }

    private void init() {
        TextView fromTextView = findViewById(R.id.path_fromText);
        TextView toTextView = findViewById(R.id.path_toText);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(PathsViewModel.class);
        fragmentManager = getSupportFragmentManager();

        from = mViewModel.getFrom();
        to = mViewModel.getTo();

        fromTextView.setText(from.getDisplayName());
        toTextView.setText(to.getDisplayName());
        setBackButtonOnClickListener();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            directionsRoute = (DirectionsRoute) extras.getSerializable("directionsResult");
            shuttleSelected = extras.getBoolean("shuttle");
        }
        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);
    }

    private void returnToSelectRoute() {
        Intent returnToSelectRoute = new Intent(PathsActivity.this,
                RoutesActivity.class);
        startActivity(returnToSelectRoute);
    }

    private void setBackButtonOnClickListener() {
        ImageButton backButton = (ImageButton) findViewById(R.id.pathsPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToSelectRoute();
            }
        });
    }

    public void showInfoCard() {
        View pathInfoCard = findViewById(R.id.path_info_card);
        BottomSheetBehavior swipeableInfoCard = BottomSheetBehavior.from(pathInfoCard);
        PathInfoCardFragment pathInfoCardFragment = new PathInfoCardFragment();
        // creating bundle to be able to pass the directionWrapper and the walkingPoints to the pathsActivity
        Bundle infoCardBundle = new Bundle();
        infoCardBundle.putSerializable("directionsResult", mViewModel.getInfoCardList());
        pathInfoCardFragment.setArguments(infoCardBundle);
        // creating fragmentTransaction to show the step-by-step card from the bottom of the screen
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.path_info_card_frame, pathInfoCardFragment);
        fragmentTransaction.commit();
        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public List<DirectionWrapper> parseDirectionResults() {
        ArrayList<DirectionWrapper> directionWrapperArrayList = new ArrayList<>();
        DirectionsStep[] steps = directionsRoute != null && directionsRoute.legs[0] != null ? directionsRoute.legs[0].steps : null;
        for (DirectionsStep step : steps) {
            DirectionWrapper directionWrapper = new DirectionWrapper();
            directionWrapper.populateAttributesFromStep(step);
            directionWrapperArrayList.add(directionWrapper);
        }
        return directionWrapperArrayList;
    }

    /**
     * checks if from and to are indoor places and stores in a boolean array {from, to}
     *
     * @param from
     * @param to
     */
    public void checkFromToType(Place from, Place to) {
        fromIsIndoor = from instanceof RoomModel;
        toIsIndoor = to instanceof RoomModel;
    }

    public void setOutdoorPaths() {
        directionWrappers = (mViewModel.areInSameBuilding(from, to) || mViewModel.arePlacesSeparatedByATunnel(from, to)) ?
                new ArrayList<>() :
                (ArrayList<DirectionWrapper>) parseDirectionResults();

        locationFragment.drawOutdoorPaths(directionWrappers);
        mViewModel.adaptOutdoorDirectionsToInfoCardList(directionWrappers);
    }

    public void setPaths() {
        // Outdoor
        if (!fromIsIndoor && !toIsIndoor) {
            setOutdoorPaths();
            return;
        }
        //Outdoor -> Indoor
        if (!fromIsIndoor) {
            setOutdoorPaths();
            locationFragment.setIndoorPaths(mViewModel.getEntrance(to), to);
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
        }
        // Indoor -> Outdoor
        else if (!toIsIndoor) {
            locationFragment.setIndoorPaths(from, mViewModel.getEntrance(from));
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
            setOutdoorPaths();
        }
        // Same Building || tunnel
        else if (mViewModel.arePlacesSeparatedByATunnel(from, to) || mViewModel.areInSameBuilding(from, to)) {
            locationFragment.setIndoorPaths(from, to);
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
        }
        //[from -> from_entrance ] + outdoor directions + [to_entrance -> to]
        else if (toIsIndoor) {
            locationFragment.setIndoorPaths(from, mViewModel.getEntrance(from));
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
            setOutdoorPaths();
            locationFragment.setIndoorPaths(mViewModel.getEntrance(to), to);
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
        }
    }

    public void setShuttlePaths() {
        //From starting point to bus stop
        // Indoor -> Entrance
        if (fromIsIndoor) {
            locationFragment.setIndoorPaths(from, mViewModel.getEntrance(from));
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
            getOutdoorDirections(mViewModel.getEntrance(from), new BusStop(from.getCampus()));
        }
        // Outdoor directions to bus stop
        else {
            getOutdoorDirections(from, new BusStop(from.getCampus()));
        }
        drawShuttlePath();
        mViewModel.adaptShuttleToInfoCardList();
        //From the bus stop to the destination
        getOutdoorDirections(new BusStop(to.getCampus()), mViewModel.getEntrance(to));
        if (toIsIndoor) {
            locationFragment.setIndoorPaths(mViewModel.getEntrance(to), to);
            mViewModel.adaptIndoorDirectionsToInfoCardList(locationFragment.getWalkingPointList());
        }
    }

    public void getOutdoorDirections(Place from, Place to) {
        LatLng fromLatLong = new LatLng(from.getCenterCoordinates().getLatitude(), from.getCenterCoordinates().getLongitude());
        LatLng toLatLong = new LatLng(to.getCenterCoordinates().getLatitude(), to.getCenterCoordinates().getLongitude());

        String url = UrlBuilder.build(fromLatLong, toLatLong, ClassConstants.WALKING);
        new DirectionsApiDataRetrieval(PathsActivity.this).execute(url, ClassConstants.WALKING);
    }

    @Override
    public void directionsApiCallBack(DirectionsResult result, List<Route> routeOptions) {
        if (result.routes.length > 0) {
            directionsRoute = result.routes[0];
            directionWrappers = (ArrayList<DirectionWrapper>) parseDirectionResults();
            locationFragment.drawOutdoorPaths(directionWrappers);
            mViewModel.adaptOutdoorDirectionsToInfoCardList(directionWrappers);
        }
    }

    public void drawShuttlePath() {
        String polyline = from.getCampus().equals("SGW") ? ClassConstants.ShuttlePolylineSGWLOY : ClassConstants.ShuttlePolylineLOYSGW;
        locationFragment.setShuttlePaths(polyline);
    }
}
