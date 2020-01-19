package com.example.concordia_campus_guide;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Button loyolaBtn;
    private Button sgwBtn;
    //For Debugging
    private static final String TAG = "MapsActivity";

    //Some Used variables which don't need to be changed
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;

    //Attributes
    private Boolean myLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        attributesInit();
        initMap();
        setupClickListeners();
        getLocationPermission();
    }


    private void attributesInit(){
    }

    private void getDeviceLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(myLocationPermissionsGranted){
                final Task location = fusedLocationProviderClient.getLastLocation();
                onCompleteListenerCurrentLocation(location);
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void onCompleteListenerCurrentLocation(Task location){
        location.addOnCompleteListener(this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();
                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                            DEFAULT_ZOOM);
                }else{
                    Log.d(TAG, "onComplete: current location is null");
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle_retro));

            if (!success) {
                Log.e("MAPACTIVITY", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MAPACTIVITY", "Can't find style. Error: ", e);
        }
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        setupPolyGonSGW(googleMap);
        uiSettingsForMap(mMap);
        zoomInLocation(45.494999, -73.577854);
    }

    private void setupPolyGonSGW(GoogleMap googleMap) {
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(45.497178, -73.579550),
                        new LatLng(45.497708, -73.579035),
                        new LatLng(45.497385, -73.578332),
                        new LatLng(45.496832, -73.578842),
                        new LatLng(45.497178, -73.579550)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
        stylePolygon(polygon1);
    }

    private void stylePolygon(Polygon polygon) {

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = ClassConstants.COLOR_WHITE_ARGB;

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(ClassConstants.POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor); //half-transparent green color
    }
    //Basically to decide what to display on the map
    private void uiSettingsForMap(GoogleMap mMap){
        if(myLocationPermissionsGranted){
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        sgwBtn = findViewById(R.id.SGWBtn);
        loyolaBtn = findViewById(R.id.loyolaBtn);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }
    private void setupClickListeners() {
        setupLoyolaBtnClickListener();
        setupSGWBtnClickListener();
    }

    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.458205, -73.640438);
            }
        });
    }

    private void setupSGWBtnClickListener(){
        sgwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }

    private void zoomInLocation(double latitude, double longitude) {
        LatLng curr = new LatLng(latitude,longitude);
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr, zoomLevel));
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(requestPermission()){
                myLocationPermissionsGranted = true;
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean requestPermission(){
        if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        myLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    //onRequestPermissionsResult: permission granted
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        myLocationPermissionsGranted = true;
                    }

                }
            }
        }
        initMap();
    }
}
