package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.RoutesHelpers.TransportType;
import com.example.concordia_campus_guide.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.model.TransitMode;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;

    TextView fromText;
    TextView toText;
    Button getDirection;

    private MarkerOptions from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = ViewModelProviders.of(this).get(RoutesActivityViewModel.class);

        //get view
        fromText = (TextView) findViewById(R.id.fromText);
        toText = (TextView) findViewById(R.id.toText);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get passed item
        mViewModel.setFrom(SelectingToFromState.getFrom());
        mViewModel.setTo(SelectingToFromState.getTo());

        // set from and to in UI
        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());

        // get all possible routes
        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DirectionsApiDataRetrieval(RoutesActivity.this).execute(buildUrl(from.getPosition(), to.getPosition(), TransportType.TRANSIT.toString()));
            }
        });

//        Double[] fromCoordinates = (mViewModel.getFrom().getCenterCoordinates());
//        Double[] toCoordinates = (mViewModel.getTo().getCenterCoordinates());
//
//        from = new MarkerOptions().position(new LatLng(fromCoordinates[0], fromCoordinates[1]));
//        to = new MarkerOptions().position(new LatLng(toCoordinates[1], toCoordinates[0]));
//
//        Log.d("mylog", "FROM:" + fromCoordinates[0] + ", " + fromCoordinates[1]);
//        Log.d("mylog", "TO:" + toCoordinates[1] + ", " + toCoordinates[0]);

        from = new MarkerOptions().position(new LatLng(45.525407, -73.677126));
        to = new MarkerOptions().position(new LatLng(45.497361,  -73.579033));

        // set back button
        setBackButtonOnClick();
    }

    public void onClickTo(View v){
        SelectingToFromState.setSelectToToTrue();
        openSearchPage();
    }

    public void onClickFrom(View v){
        SelectingToFromState.setSelectFromToTrue();
        openSearchPage();
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = (ImageButton)this.findViewById(R.id.routesPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { exitSelectToFrom(); }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            exitSelectToFrom();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void exitSelectToFrom(){
        Intent exitSelectToFrom= new Intent(RoutesActivity.this,
                MainActivity.class);

        SelectingToFromState.reset();

        startActivity(exitSelectToFrom);
    }

    private void openSearchPage(){
        Intent openSearch= new Intent(RoutesActivity.this,
                SearchActivity.class);

        startActivity(openSearch);
    }

    /**
     * Build the URL that will be used to call the Google Maps Directions API by passing the necessary parameters
     *
     * @param from: latitude and longitude of the origin
     * @param to: latitude and longitude of the destination
     * @param transportType: main transport type to get from origin to destination
     *
     */
    private String buildUrl(LatLng from, LatLng to, String transportType) {
        // Origin of route
        String str_origin = "origin=" + from.latitude + "," + from.longitude;
        // Destination of route
        String str_dest = "destination=" + to.latitude + "," + to.longitude;
        // Mode
        String mode = "mode=" + transportType;
        // Alternatives
        String alternatives = "alternatives=true";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode + "&" + alternatives;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + BuildConfig.API_KEY;

        return url;
    }

}