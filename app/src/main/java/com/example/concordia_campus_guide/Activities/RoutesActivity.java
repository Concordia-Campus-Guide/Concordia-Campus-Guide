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

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;

    TextView fromText;
    TextView toText;
    Button getDirection;

    private MarkerOptions from;
    private MarkerOptions to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(RoutesActivityViewModel.class);

        //get view
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get passed item
        mViewModel.setFrom(SelectingToFromState.getFrom());
        mViewModel.setTo(SelectingToFromState.getTo());

        // set from and to in UI
        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());

        // set from and to
        setFrom();
        setTo();

        // set back button
        setBackButtonOnClick();

        // get all possible routes
        getDirection = findViewById(R.id.btnGetDirection); // dummy button for now to test if we can retrieve all routes correctly starting from the UI
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mViewModel.buildUrl(from.getPosition(), to.getPosition(), ClassConstants.TRANSIT);
                new DirectionsApiDataRetrieval(RoutesActivity.this).execute(url);
            }
        });
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
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            exitSelectToFrom();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void directionsApiCallBack(DirectionsResult result)
    {
        mViewModel.setDirectionsResult(result);
        System.out.println("lol");
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = (ImageButton)this.findViewById(R.id.routesPageBackButton);
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
        Intent openSearch= new Intent(RoutesActivity.this,
                SearchActivity.class);

        startActivity(openSearch);
    }

    private void setFrom() {
        Double[] toCoordinates = (mViewModel.getTo().getCenterCoordinates());
        to = new MarkerOptions().position(new LatLng(toCoordinates[1], toCoordinates[0]));

    }

    private void setTo() {
        Double[] fromCoordinates = (mViewModel.getFrom().getCenterCoordinates());
        from = new MarkerOptions().position(new LatLng(fromCoordinates[0], fromCoordinates[1]));
    }
}