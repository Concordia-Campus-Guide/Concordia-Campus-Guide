package com.example.concordia_campus_guide.LocationFragment;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.concordia_campus_guide.ClassConstants;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class LocationFragment extends Fragment{
    MapView mMapView;
    private GoogleMap mMap;
    private LocationFragmentViewModel mViewModel;
    private GeoJsonLayer mLayer;
    private Button loyolaBtn;
    private Button sgwBtn;
    private Boolean myLocationPermissionsGranted = false;


    /**
     * @return it will return a new object of this fragment
     */
    public static LocationFragment newInstance() {
        return new LocationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment_fragment, container, false);
        initComponent(rootView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        initMap();
        setupClickListeners();
        getLocationPermission();
        return rootView;
    }


    /**
     * @param rootView is the object that contains the parts displayed on the fragment
     */
    private void initComponent(View rootView) {
        mMapView = rootView.findViewById(R.id.mapView);
        sgwBtn = rootView.findViewById(R.id.SGWBtn);
        loyolaBtn = rootView.findViewById(R.id.loyolaBtn);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel.class);
    }


    /**
     * The purpose of this method is to init the map and fill it up with the required
     * information to display them to the user
     */
    private void initMap() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setMapStyle(googleMap);
                mMap = googleMap;
                //mMap.setMapType(googleMap.MAP_TYPE_TERRAIN);
                setupPolygons(mMap);
                uiSettingsForMap(mMap);
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }


    /**
     * The purpose of this method is to figure the style of the map to display
     * @param googleMap is the map that is used in the application
     */
    private void setMapStyle(GoogleMap googleMap) {
        try {
            googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), mViewModel.getMapStyle()));
        } catch (Resources.NotFoundException e) {
            Log.e("MAPACTIVITY", "Can't find style. Error: ", e);
        }
    }


    /**
     * The purpose of this method is to setup the click listener for both
     * SGW and LOYALA Campuses buttons.
     */
    private void setupClickListeners() {
        setupLoyolaBtnClickListener();
        setupSGWBtnClickListener();
    }


    /**
     * The purpose of this method is to handle the "on click" of Loyala button
     */
    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.458205, -73.640438);
            }
        });
    }

    /**
     * The purpose of this method is to handle the "on click" of SGW button
     */
    private void setupSGWBtnClickListener(){
        sgwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }

    /**
     * The purpose of this method is display the polygon on the map and
     * call the right method for onClick polygon or on click the marker.
     * @param map is the map to be used in our application
     */
    private void setupPolygons(GoogleMap map) {
        mLayer = mViewModel.loadPolygons(map, getContext());
        setupPolygonClickListener();
        mLayer.addLayerToMap();
        setupMarkerClickListener(map);
    }


    /**
     * The purpose of this method is handle the onclick polygon
     * and to open the info card according to the clicked building.
     */
    public void setupPolygonClickListener(){
        mLayer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                //TODO: CCG-4 Make function that pops up the info card for the building (via the building-code)
                //Important null check do not remove!
                if(geoJsonFeature != null){
                    //replace code here
                    System.out.println("Clicked on "+geoJsonFeature.getProperty("code"));
                }
            }
        });
    }

    /**
     * The purpose of this method is handle the onclick marker
     * and to open the info card according to the clicked building.
     */
    public boolean setupMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //TODO: CCG-4 Make function that pops up the info card for the building (via the building-code)
                System.out.println(marker.getTag());
                return false;
            }
        });
        return true;
    }

    /**
     * The purpose of this method is to display the tools used with google
     * maps such as Current Location
     * @param mMap is the map used in the application
     */
    private void uiSettingsForMap(GoogleMap mMap){
        if(myLocationPermissionsGranted){
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }


    /**
     * @param latitude is the chosen latitude on the map to zoom in
     * @param longitude is the chosen longitude on the map to zoom in
     */
    private void zoomInLocation(double latitude, double longitude) {
        LatLng curr = new LatLng(latitude,longitude);
        float zoomLevel = 18.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr, zoomLevel));
    }

    /**
     * The purpose of this application is to ask the user for their permission
     * of using their current location.
     */
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(requestPermission()){
            myLocationPermissionsGranted = true;
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    ClassConstants.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    /**
     * @return the method returns true if the user accepts to give the application permission
     * for using their current location.
     */
    private boolean requestPermission(){
        return (checkSelfPermission(getContext(), ClassConstants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == ClassConstants.LOCATION_PERMISSION_REQUEST_CODE)
            myLocationPermissionsGranted = (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
