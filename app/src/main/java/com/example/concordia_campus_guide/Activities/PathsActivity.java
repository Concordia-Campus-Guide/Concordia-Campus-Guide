package com.example.concordia_campus_guide.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Fragments.PathInfoCardFragment.PathInfoCardFragment;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);

        fromTextView = (TextView) findViewById(R.id.path_fromText);
        toTextView = (TextView) findViewById(R.id.path_toText);

        mViewModel = ViewModelProviders.of(this).get(PathsViewModel.class);
        fragmentManager = getSupportFragmentManager();

        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);

        Place from = mViewModel.getFrom();
        Place to = mViewModel.getTo();

        fromTextView.setText(from.getDisplayName());
        toTextView.setText(to.getDisplayName());
        setBackButtonOnClickListener();


        locationFragment.drawPaths(from, to);
        locationFragment.setLocationToDisplay(new LatLng(-73.57901685, 45.49761115));

        View pathInfoCard = findViewById(R.id.path_info_card);
        swipeableInfoCard = BottomSheetBehavior.from(pathInfoCard);
        showInfoCard();
    }

    private void returnToSelectRoute(){
        Intent returnToSelectRoute= new Intent(PathsActivity.this,
                RoutesActivity.class);

        startActivity(returnToSelectRoute);
    }

    private void setBackButtonOnClickListener(){
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
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.path_info_card_frame, pathInfoCardFragment);
        fragmentTransaction.commit();
        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
