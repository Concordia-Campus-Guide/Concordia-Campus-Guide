package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.UrlBuilder;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;
    TextView fromText;
    TextView toText;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(RoutesActivityViewModel.class);
        mViewModel.setShuttles();

        // get view
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        content = findViewById(R.id.content);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get passed item
        mViewModel.setFrom(SelectingToFromState.getFrom());
        mViewModel.setTo(SelectingToFromState.getTo());

        // set from and to in UI
        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());

        // set back button
        setBackButtonOnClick();

        // get all possible routes. The following statements should happen on an onClick event.
        String url = UrlBuilder.build(new LatLng(mViewModel.getFrom().getCenterCoordinates().getLatitude(), mViewModel.getFrom().getCenterCoordinates().getLongitude()), new LatLng(mViewModel.getTo().getCenterCoordinates().getLatitude(), mViewModel.getTo().getCenterCoordinates().getLongitude()), ClassConstants.TRANSIT);
        new DirectionsApiDataRetrieval(RoutesActivity.this).execute(url);
    }

    public void onClickTo(View v){
        SelectingToFromState.setSelectToToTrue();
        openSearchPage();
    }

    public void onClickFrom(View v){
        SelectingToFromState.setSelectFromToTrue();
        openSearchPage();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitSelectToFrom();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void directionsApiCallBack(DirectionsResult result)
    {
        mViewModel.setDirectionsResult(result);
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = this.findViewById(R.id.routesPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { exitSelectToFrom(); }
        });
    }

    private void exitSelectToFrom(){
        Intent exitSelectToFrom= new Intent(RoutesActivity.this,
                MainActivity.class);

        SelectingToFromState.reset();

        startActivity(exitSelectToFrom);
    }

    private void openSearchPage(){
        Intent openSearch= new Intent(RoutesActivity.this, SearchActivity.class);
        startActivity(openSearch);
    }

    public void getShuttle(View view) {
        List<Shuttle> shuttles = mViewModel.getShuttles();
        String content = mViewModel.getShuttleDisplayText(shuttles);
        this.content.setText(content);
    }
}