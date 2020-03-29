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
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Fragments.PathInfoCardFragment.PathInfoCardFragment;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class PathsActivity extends AppCompatActivity {
    private PathsViewModel mViewModel;
    private LocationFragment locationFragment;
    private TextView fromTextView;
    private TextView toTextView;
    private ImageButton backButton;
    private PathInfoCardFragment pathInfoCardFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private BottomSheetBehavior swipeableInfoCard;
    private DirectionsRoute directionsResult;
    private ArrayList<DirectionWrapper> directionWrappers;
    private Place from;
    private Place to;
    private boolean fromIsIndoor = false;
    private boolean toIsIndoor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);

        fromTextView = (TextView) findViewById(R.id.path_fromText);
        toTextView = (TextView) findViewById(R.id.path_toText);

        mViewModel = new ViewModelProvider(this).get(PathsViewModel.class);
        fragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            directionsResult = (DirectionsRoute) extras.getSerializable("directionsResult");
        }

        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);

        from = mViewModel.getFrom();
        to = mViewModel.getTo();
        checkFromToType(from, to);

        fromTextView.setText(from.getDisplayName());
        toTextView.setText(to.getDisplayName());
        setBackButtonOnClickListener();

        directionWrappers = !(fromIsIndoor && toIsIndoor) ? (ArrayList<DirectionWrapper>) parseDirectionResults() : null;
        if (!(fromIsIndoor && toIsIndoor)) {
            locationFragment.drawOutdoorPaths(directionWrappers);
        }

        View pathInfoCard = findViewById(R.id.path_info_card);
        swipeableInfoCard = BottomSheetBehavior.from(pathInfoCard);
        setIndoorPaths();
        showInfoCard();
    }

    private void returnToSelectRoute() {
        Intent returnToSelectRoute = new Intent(PathsActivity.this,
                RoutesActivity.class);
        startActivity(returnToSelectRoute);
    }

    private void setBackButtonOnClickListener() {
        backButton = (ImageButton) findViewById(R.id.pathsPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToSelectRoute();
            }
        });
    }

    public void showInfoCard() {
        pathInfoCardFragment = new PathInfoCardFragment();
        // creating bundle to be able to pass the directionWrapper and the walkingPoints to the pathsActivity
        Bundle infoCardBundle = new Bundle();
        infoCardBundle.putSerializable("directionsResult", directionWrappers);
        if(fromIsIndoor || toIsIndoor) infoCardBundle.putSerializable("walkingPoints", (ArrayList<WalkingPoint>) locationFragment.getWalkingPointList());
        pathInfoCardFragment.setArguments(infoCardBundle);
        // creating fragmentTransaction to show the step-by-step card from the bottom of the screen
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.path_info_card_frame, pathInfoCardFragment);
        fragmentTransaction.commit();
        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public List<DirectionWrapper> parseDirectionResults() {
        ArrayList<DirectionWrapper> directionWrapperArrayList = new ArrayList<>();
        DirectionsStep[] steps = directionsResult.legs[0].steps;
        for (DirectionsStep step : steps) {
            DirectionWrapper directionWrapper = new DirectionWrapper();
            directionWrapper.populateAttributesFromStep(step);
            directionWrapperArrayList.add(directionWrapper);
        }
        return directionWrapperArrayList;
    }

    /**
     *  checks if from and to are indoor places and stores in a boolean array {from, to}
     * @param from
     * @param to
     */
    public void checkFromToType(Place from, Place to) {
        fromIsIndoor = from instanceof RoomModel;
        toIsIndoor = to instanceof RoomModel;
    }

    public void setIndoorPaths() {
        if(!fromIsIndoor && !toIsIndoor) return;

        String floorCode;
        Place tempFrom = from;
        Place tempTo = to;

        if(fromIsIndoor && !toIsIndoor){
            floorCode = ((RoomModel) from).getFloorCode();
            tempTo = new RoomModel(from.getCenterCoordinates(), floorCode.substring(0, floorCode.indexOf('-')), floorCode);
        }
        if(toIsIndoor && !fromIsIndoor){
            floorCode = ((RoomModel) to).getFloorCode();
            tempFrom = new RoomModel(to.getCenterCoordinates(), floorCode.substring(0, floorCode.indexOf('-')), floorCode);
        }

        locationFragment.setIndoorPaths(tempFrom, tempTo);
    }
}
