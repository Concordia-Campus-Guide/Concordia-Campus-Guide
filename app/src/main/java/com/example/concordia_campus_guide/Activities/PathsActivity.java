package com.example.concordia_campus_guide.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Fragments.PathInfoCardFragment.PathInfoCardFragment;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PathsActivity extends AppCompatActivity {
    PathsViewModel mViewModel;
    LocationFragment locationFragment;
    TextView fromTextView;
    TextView toTextView;
    ImageButton backButton;
    PathInfoCardFragment pathInfoCardFragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private BottomSheetBehavior swipeableInfoCard;
    private DirectionsResult directionsResult;
    private ArrayList<DirectionWrapper> directionWrappers;
    Place from;
    Place to;
    private boolean fromIsIndoor = false;
    private boolean toIsIndoor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);

        fromTextView = (TextView) findViewById(R.id.path_fromText);
        toTextView = (TextView) findViewById(R.id.path_toText);

        mViewModel = ViewModelProviders.of(this).get(PathsViewModel.class);
        fragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            directionsResult = (DirectionsResult) extras.getSerializable("directionsResult");
        }

        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);

        from = mViewModel.getFrom();
        to = mViewModel.getTo();
        checkFromToType(from, to);

        fromTextView.setText(from.getDisplayName());
        toTextView.setText(to.getDisplayName());
        setBackButtonOnClickListener();

        directionWrappers = !(fromIsIndoor && toIsIndoor) ? (ArrayList<DirectionWrapper>) parseDirectionResults() : new ArrayList<DirectionWrapper>();
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
        Bundle infoCardBundle = new Bundle();
        infoCardBundle.putSerializable("directionsResult", directionWrappers);
        if(fromIsIndoor || toIsIndoor) infoCardBundle.putSerializable("walkingPoints", (ArrayList<WalkingPoint>) locationFragment.getWalkingPointList());
        pathInfoCardFragment.setArguments(infoCardBundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.path_info_card_frame, pathInfoCardFragment);
        fragmentTransaction.commit();
        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public List<DirectionWrapper> parseDirectionResults() {
        ArrayList<DirectionWrapper> directionWrapperArrayList = new ArrayList<>();
        DirectionsStep[] steps = directionsResult.routes[0].legs[0].steps;
        for (DirectionsStep step : steps) {
            directionWrapperArrayList.add(new DirectionWrapper(step));
        }
        return directionWrapperArrayList;
    }
    //checks if from and to are indoor places and stores in a boolean array {from, to}
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
