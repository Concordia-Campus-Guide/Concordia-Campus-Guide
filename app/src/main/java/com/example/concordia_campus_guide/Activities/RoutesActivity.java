package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.Adapters.RoutesAdapter;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsRoute;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.UrlBuilder;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Interfaces.DirectionsApiCallbackListener;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RoutesActivity extends AppCompatActivity implements DirectionsApiCallbackListener {

    RoutesActivityViewModel mViewModel;
    TextView fromText;
    TextView toText;
    TextView content;
    ListView allRoutes;
    RoutesAdapter adapter;

    ImageButton transitButton;
    ImageButton shuttleButton;
    ImageButton walkButton;
    ImageButton carButton;
    ImageButton disabilityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        setViewModelAttributes();
        setToolbar();
        setFromAndTo();
        setBackButtonOnClick();
        getAllRoutes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Fetching the stored data from the SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);

        String disability = sharedPreferences.getString(ClassConstants.DISABILITY_BUTTON, ClassConstants.FALSE);

        if (disability.equals(ClassConstants.TRUE)) {
            disabilityButton.setSelected(true);
        } else {
            disabilityButton.setSelected(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object with a file name "UserPreferences" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        String disabilityBooleanValue = disabilityButton.isSelected() ? ClassConstants.TRUE : ClassConstants.FALSE;
        myEdit.putString(ClassConstants.DISABILITY_BUTTON, disabilityBooleanValue);
        myEdit.commit();
    }

    private void initComponent() {
        setContentView(R.layout.routes_activity);
        allRoutes = findViewById(R.id.allRoutes);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(RoutesActivityViewModel.class);

        // get view
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        content = findViewById(R.id.content);

        // set up listeners for buttons
        transitButton = findViewById(R.id.filterButtonTransit);
        shuttleButton = findViewById(R.id.filterButtonShuttle);
        walkButton = findViewById(R.id.filterButtonWalk);
        carButton = findViewById(R.id.filterButtonCar);
        disabilityButton = findViewById(R.id.filterButtonDisability);
    }

    private void setFromAndTo() {
        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setViewModelAttributes() {
        mViewModel.setFrom(SelectingToFromState.getFrom());
        mViewModel.setTo(SelectingToFromState.getTo());
    }

    public void onClickTo(View v) {
        SelectingToFromState.setSelectToToTrue();
        openSearchPage();
    }

    public void onClickFrom(View v) {
        SelectingToFromState.setSelectFromToTrue();
        openSearchPage();
    }

    public void onClickTransit(View v) {
        mViewModel.setTransportType(ClassConstants.TRANSIT);
        getAllRoutes();
        setTransitSelect();
    }

    private void setTransitSelect() {
        transitButton.setSelected(true);
        shuttleButton.setSelected(false);
        walkButton.setSelected(false);
        carButton.setSelected(false);
    }

    public void onClickCar(View v) {
        mViewModel.setTransportType(ClassConstants.DRIVING);
        getAllRoutes();
        setCarSelect();
    }

    private void setCarSelect() {
        transitButton.setSelected(false);
        shuttleButton.setSelected(false);
        walkButton.setSelected(false);
        carButton.setSelected(true);
    }

    // TODO: #180
    public void onClickDisability(View v) {
        disabilityButton.setSelected(!disabilityButton.isSelected());
    }

    public void onClickShuttle(View v) {
        mViewModel.setRouteOptions(new ArrayList<>());
        List<Shuttle> shuttles = mViewModel.filterShuttles();
        if (!shuttles.isEmpty()) {
            mViewModel.adaptShuttleToRoutes(shuttles);
        } else {
            mViewModel.noShuttles(this);
        }
        setShuttleSelect();
        setRoutesAdapter();
    }

    private void setShuttleSelect() {
        transitButton.setSelected(false);
        shuttleButton.setSelected(true);
        walkButton.setSelected(false);
        carButton.setSelected(false);
    }

    public void onClickWalk(View v) {
        mViewModel.setTransportType(ClassConstants.WALKING);
        getAllRoutes();
        setWalkSelect();
    }

    private void setWalkSelect() {
        transitButton.setSelected(false);
        shuttleButton.setSelected(false);
        walkButton.setSelected(true);
        carButton.setSelected(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitSelectToFrom();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void directionsApiCallBack(DirectionsResult result, List<Route> routeOptions) {
        mViewModel.setDirectionsResult(result);
        mViewModel.setRouteOptions(routeOptions);

        setRoutesAdapter();
    }

    private void setRoutesAdapter() {
        // Android adapter for list view
        adapter = new RoutesAdapter(this, R.layout.list_routes, mViewModel.getRouteOptions());
        allRoutes.setAdapter(adapter);
        allRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (carButton.isSelected() || transitButton.isSelected() || walkButton.isSelected()) {
                    Intent openPaths = new Intent(RoutesActivity.this,
                            PathsActivity.class);
                    DirectionsRoute directionsResult = mViewModel.getDirectionsResult().routes[i];
                    openPaths.putExtra("directionsResult", directionsResult);
                    openPaths.putExtra("shuttle", false);
                    startActivity(openPaths);
                } else {
                    if (mViewModel.getShuttles() == null) {
                        Toast.makeText(getApplicationContext(), "Paths view for Shuttle route is not available if no shuttles exist.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent openPaths = new Intent(RoutesActivity.this,
                                PathsActivity.class);
                        openPaths.putExtra("shuttle", true);
                        startActivity(openPaths);
                    }
                }
            }
        });
    }

    private void setBackButtonOnClick() {
        ImageButton backButton = this.findViewById(R.id.routesPageBackButton);
        backButton.setOnClickListener(v -> exitSelectToFrom());
    }

    private void exitSelectToFrom() {
        Intent exitSelectToFrom = new Intent(RoutesActivity.this,
                MainActivity.class);
        SelectingToFromState.reset();
        startActivity(exitSelectToFrom);
    }

    private void openSearchPage() {
        Intent openSearch = new Intent(RoutesActivity.this, SearchActivity.class);
        startActivity(openSearch);
    }

    /**
     * Calls the google Maps Directions API
     */
    public void getAllRoutes() {
        setTransitSelect();

        Coordinates fromCenterCoordinates = mViewModel.getEntrance(mViewModel.getFrom()).getCenterCoordinates();
        Coordinates toCenterCoordinates = mViewModel.getEntrance(mViewModel.getTo()).getCenterCoordinates();

        if (fromCenterCoordinates != null && toCenterCoordinates != null) {
            LatLng from = new LatLng(fromCenterCoordinates.getLatitude(), fromCenterCoordinates.getLongitude());
            LatLng to = new LatLng(toCenterCoordinates.getLatitude(), toCenterCoordinates.getLongitude());
            String transportType = mViewModel.getTransportType();

            String url = UrlBuilder.build(from, to, transportType,  getBaseContext().getResources().getConfiguration().getLocales().get(0));
            new DirectionsApiDataRetrieval(RoutesActivity.this).execute(url, transportType);
        }
    }
}